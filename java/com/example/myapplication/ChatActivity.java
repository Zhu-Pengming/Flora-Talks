package com.example.myapplication;


import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapter.ChatListViewAdapter;
import com.example.myapplication.adapter.ChatRecyclerAdapter;
import com.example.myapplication.animator.SlideInOutBottomItemAnimator;
import com.example.myapplication.common.ChatConst;
import com.example.myapplication.db.ChatMessageBean;
import com.example.myapplication.ui.BaseActivity;
import com.example.myapplication.utils.KeyBoardUtils;
import com.example.myapplication.widget.PullToRefreshRecyclerView;
import com.example.myapplication.widget.PullToRefreshView;
import com.example.myapplication.widget.WrapContentLinearLayoutManager;
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;

public class ChatActivity extends BaseActivity {
    private PullToRefreshRecyclerView myList;
    private ChatRecyclerAdapter tbAdapter;
    private ChatActivity.SendMessageHandler sendMessageHandler;
    private WrapContentLinearLayoutManager wcLinearLayoutManger;

    private String Content;

    private Interpreter tflite;

    public static final int MESSAGE_RECEIVED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findView() {
        super.findView();
        pullList.setSlideView(new PullToRefreshView(this).getSlideView(PullToRefreshView.RECYCLERVIEW));
        myList = (PullToRefreshRecyclerView) pullList.returnMylist();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        tblist.clear();
        tbAdapter.notifyDataSetChanged();
        myList.setAdapter(null);
        sendMessageHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected void init() {
        setTitle("RecyclerView");
        tbAdapter = new ChatRecyclerAdapter(this, tblist);
        wcLinearLayoutManger = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myList.setLayoutManager(wcLinearLayoutManger);
        myList.setItemAnimator(new SlideInOutBottomItemAnimator(myList));
        myList.setAdapter(tbAdapter);
        sendMessageHandler = new ChatActivity.SendMessageHandler(ChatActivity.this);
        tbAdapter.isPicRefresh = true;
        tbAdapter.notifyDataSetChanged();
        tbAdapter.setSendErrorListener(new ChatRecyclerAdapter.SendErrorListener() {

            @Override
            public void onClick(int position) {
                // TODO Auto-generated method stub
                ChatMessageBean tbub = tblist.get(position);
                if (tbub.getType() == ChatRecyclerAdapter.TO_USER_IMG) {
                    sendMixedMessage(null, tbub.getImageLocal());
                    tblist.remove(position);
                }
            }

        });
        tbAdapter.setVoiceIsReadListener(new ChatRecyclerAdapter.VoiceIsRead() {

            @Override
            public void voiceOnClick(int position) {
                // TODO Auto-generated method stub
                for (int i = 0; i < tbAdapter.unReadPosition.size(); i++) {
                    if (tbAdapter.unReadPosition.get(i).equals(position + "")) {
                        tbAdapter.unReadPosition.remove(i);
                        break;
                    }
                }
            }

        });

        myList.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {
                // TODO Auto-generated method stub
                switch (scrollState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        tbAdapter.handler.removeCallbacksAndMessages(null);
                        tbAdapter.setIsGif(true);
                        tbAdapter.isPicRefresh = false;
                        tbAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        tbAdapter.handler.removeCallbacksAndMessages(null);
                        tbAdapter.setIsGif(false);
                        tbAdapter.isPicRefresh = true;
                        reset();
                        KeyBoardUtils.hideKeyBoard(ChatActivity.this,
                                mEditTextContent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        controlKeyboardLayout(activityRootView, pullList);
        super.init();
    }

    /**
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final View needToScrollView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                ChatActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = ChatActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                int recyclerHeight = 0;
                if (wcLinearLayoutManger != null) {
                    recyclerHeight = wcLinearLayoutManger.getRecyclerHeight();
                }
                if (heightDifference == 0 || heightDifference == bottomStatusHeight) {
                    needToScrollView.scrollTo(0, 0);
                } else {
                    if (heightDifference < recyclerHeight) {
                        int contentHeight = wcLinearLayoutManger == null ? 0 : wcLinearLayoutManger.getHeight();
                        if (recyclerHeight < contentHeight) {
                            listSlideHeight = heightDifference - (contentHeight - recyclerHeight);
                            needToScrollView.scrollTo(0, listSlideHeight);
                        } else {
                            listSlideHeight = heightDifference;
                            needToScrollView.scrollTo(0, listSlideHeight);
                        }
                    } else {
                        listSlideHeight = 0;
                    }
                }
            }
        });
    }

    @Override
    protected void loadRecords() {
        isDown = true;
        if (pagelist != null) {
            pagelist.clear();
        }
        pagelist = mChatDbManager.loadPages(page, number);
        position = pagelist.size();
        if (pagelist.size() != 0) {
            pagelist.addAll(tblist);
            tblist.clear();
            tblist.addAll(pagelist);
            if (imageList != null) {
                imageList.clear();
            }
            if (imagePosition != null) {
                imagePosition.clear();
            }
            int key = 0;
            int position = 0;
            for (ChatMessageBean cmb : tblist) {
                if (cmb.getType() == ChatListViewAdapter.FROM_USER_IMG || cmb.getType() == ChatListViewAdapter.TO_USER_IMG) {
                    imageList.add(cmb.getImageLocal());
                    imagePosition.put(key, position);
                    position++;
                }
                key++;
            }
            tbAdapter.setImageList(imageList);
            tbAdapter.setImagePosition(imagePosition);
            sendMessageHandler.sendEmptyMessage(PULL_TO_REFRESH_DOWN);
            if (page == 0) {
                pullList.refreshComplete();
                pullList.setPullGone();
            } else {
                page--;
            }
        } else {
            if (page == 0) {
                pullList.refreshComplete();
                pullList.setPullGone();
            }
        }
    }

    static class SendMessageHandler extends Handler {
        WeakReference<ChatActivity> mActivity;

        SendMessageHandler(ChatActivity activity) {
            mActivity = new WeakReference<ChatActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            ChatActivity theActivity = mActivity.get();
            if (theActivity != null) {
                switch (msg.what) {
                    case REFRESH:
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyDataSetChanged();
                        int position = theActivity.tbAdapter.getItemCount() - 1 < 0 ? 0 : theActivity.tbAdapter.getItemCount() - 1;
                        theActivity.myList.smoothScrollToPosition(position);
                        break;
                    case SEND_OK:
                        theActivity.mEditTextContent.setText("");
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyItemInserted(theActivity.tblist
                                .size() - 1);
                        theActivity.myList.smoothScrollToPosition(theActivity.tbAdapter.getItemCount() - 1);
                        break;
                    case RECERIVE_OK:
                        theActivity.tbAdapter.isPicRefresh = true;
                        theActivity.tbAdapter.notifyItemInserted(theActivity.tblist
                                .size() - 1);
                        theActivity.myList.smoothScrollToPosition(theActivity.tbAdapter.getItemCount() - 1);
                        break;
                    case PULL_TO_REFRESH_DOWN:
                        theActivity.pullList.refreshComplete();
                        theActivity.tbAdapter.notifyDataSetChanged();
                        theActivity.myList.smoothScrollToPosition(theActivity.position - 1);
                        theActivity.isDown = false;
                        break;
                    default:
                        break;
                }
            }
        }

    }
    @Override
    protected void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
// 获取用户输入的消息
                final String content = mEditTextContent.getText().toString();

// 获取最后一张图片的路径
                String imagepath = null;
                if (!imageList.isEmpty()) {
                    imagepath = imageList.get(imageList.size() - 1);
                }

// 将消息显示在聊天界面
                appendMessageToChat(content, ChatListViewAdapter.TO_USER_MSG);

// 向机器人发送消息，并获取机器人的回复
                final String botResponse = sendMixedMessage(content, imagepath);

// 将机器人的回复显示在聊天界面
                appendMessageToChat(botResponse, ChatListViewAdapter.FROM_USER_MSG);
            }
        }).start();
    }

        // 向机器人发送消息，并接收回应
    // 将消息显示在聊天界面
    private void appendMessageToChat(final String message, final int msgType) {
            // 在 UI 线程上更新聊天界面
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tblist.add(getTbub(msgType == ChatListViewAdapter.TO_USER_MSG ? userName : "Flora Talk", msgType, message, null, null, null, null, null, 0f, ChatConst.COMPLETED));
                tbAdapter.notifyItemInserted(tblist.size() - 1);
                myList.smoothScrollToPosition(tbAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    protected String sendMixedMessage(String text, String imagePath) {

        String content = mEditTextContent.getText().toString();

        // 检测消息类型
        if (isTextMessage(content)) {
            // 发送文本消息到机器人
            processMessage(text,imagePath);
        } else if (isImageFileType(imagePath)) {
            // 发送图片消息到机器人
            processMessage(text,imagePath);
        }

        // 清空输入框内容
        mEditTextContent.setText("");

        return content;
    }

    private boolean isTextMessage(String content) {
        return !TextUtils.isEmpty(content);
    }


    private boolean isImageFileType(String filePath) {
        String type = FileTypeUtil.getType(FileUtil.file(filePath));
        return type != null && type.startsWith("image");
    }

    private void processMessage(String receivedMessage,String filePath) {

        //CV

        String CV_output ="";



        // Hugging-face
        ImageToText imageCaptioningTask = new ImageToText();
        String description = String.valueOf(imageCaptioningTask.execute(filePath));


        //NLP
        try {
            tflite = new Interpreter(loadModelFile(this.getAssets(), "model.tflite"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Prepare input data
        String[] inputString = new String[1];
        inputString[0] = receivedMessage+CV_output+description;
        float[][] outputVal = new float[1][1];

        // Run model
        tflite.run(inputString, outputVal);


    }
    private MappedByteBuffer loadModelFile(AssetManager assetManage, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManage.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


}