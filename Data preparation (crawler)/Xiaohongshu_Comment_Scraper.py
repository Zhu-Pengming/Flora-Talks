import requests
from DataRecorder import Recorder
import time
import random
from tqdm import tqdm


def read_urls_from_txt(file_path):
    with open(file_path, 'r') as file:
        note_urls = [line.strip() for line in file.readlines()]
    return note_urls


def trans_date(create_time):
    # Convert a 13-bit timestamp
    timeStamp = create_time / 1000
    timeArray = time.localtime(timeStamp)
    otherStyleTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    return otherStyleTime


def get_comments():
    # Split note note_url to extract the last/last digit, that is, note_id
    note_id = note_url.split("/")[-1]
    page = 1
    while True:
        if page == 1:
            url = f'https://edith.xiaohongshu.com/api/sns/web/v2/comment/page?note_id={note_id}&top_comment_id=&image_scenes=FD_WM_WEBP,CRD_WM_WEBP'
        else:
            url = f'https://edith.xiaohongshu.com/api/sns/web/v2/comment/page?note_id={note_id}&top_comment_id=&image_scenes=FD_WM_WEBP,CRD_WM_WEBP&cursor={next_cursor}'
        # Send a request
        response = requests.get(url, headers=headers)

        if str(response.status_code) != '200':
            continue

        # Receive the returned data in json format
        json_response = response.json()
        # print(json_response)

        print(json_response)

        # Number of current page level comments
        comment_num = len(json_response['data']['comments'])
        print(f"**********第{page}页有{comment_num}条评论**********")

        # Generate a random time
        random_time = random.uniform(1, 2)
        # pause
        time.sleep(random_time)

        # Extract level 1 comments
        for comment in json_response['data']['comments']:
            # Comment level
            comment_level = '一级评论'
            # Comment id
            id = comment['id']
            # Commenter nickname
            nickname = comment['user_info']['nickname']
            # Commenter id
            user_id = comment['user_info']['user_id']
            # Commenter home link
            user_link = 'https://www.xiaohongshu.com/user/profile/' + user_id
            # comment content
            content = comment['content']
            # Comment likes
            like_count = comment['like_count']
            # Comment IP dependencies
            try:
                ip = comment['ip_location']
            except:
                ip = ''
            # Comment time
            create_time = trans_date(comment['create_time'])

            # Root comment id
            root_comment_id = comment['id']
            print(f"评论位置：{comment_level}, 评论时间：{create_time}, 评论内容：{content}")

            data = {'笔记链接': note_url,
                    '评论id': id,
                    '评论级别': comment_level,
                    '页码': page,
                    '评论者昵称': nickname,
                    '评论者id': user_id,
                    '评论者主页链接': user_link,
                    '评论内容': content,
                    '评论时间': create_time,
                    '评论IP属地': ip,
                    '评论点赞数': like_count
                    }
            # read-in data
            r.add_data(data)

            # Number of secondary reviews
            sub_comment_count = comment['sub_comment_count']

            if int(sub_comment_count) > 0:
                for sub_comment in comment['sub_comments']:
                    # Comment level
                    comment_level2 = '二级评论'
                    # Comment id
                    id2 = sub_comment['id']
                    # Commenter id
                    user_id2 = sub_comment['user_info']['user_id']
                    # Commenter nickname
                    nickname2 = sub_comment['user_info']['nickname']
                    # Commenter home link
                    user_link2 = 'https://www.xiaohongshu.com/user/profile/' + user_id2
                    # Comment time
                    create_time2 = trans_date(sub_comment['create_time'])
                    # Comment likes
                    like_count2 = sub_comment['like_count']
                    # Comment IP dependencies
                    try:
                        ip2 = sub_comment['ip_location']
                    except:
                        ip2 = ''
                    # comment content
                    content2 = sub_comment['content']
                    print(f"评论位置：{comment_level2}, 评论时间：{create_time2}, 评论内容：{content2}")
                    data2 = {'笔记链接': note_url,
                             '评论id': id2,
                             '评论级别': comment_level2,
                             '页码': page,
                             '评论者昵称': nickname2,
                             '评论者id': user_id2,
                             '评论者主页链接': user_link2,
                             '评论内容': content2,
                             '评论时间': create_time2,
                             '评论IP属地': ip2,
                             '评论点赞数': like_count2
                             }
                    # read-in data
                    r.add_data(data2)

            # Open a comment
            if comment['sub_comment_has_more'] == True:
                extend_page = 1
                while True:
                    if extend_page == 1:
                        url_more = f'https://edith.xiaohongshu.com/api/sns/web/v2/comment/sub/page?note_id={note_id}&root_comment_id={root_comment_id}&image_scenes=FD_WM_WEBP,CRD_WM_WEBP&cursor={comment["sub_comment_cursor"]}&num=10'
                    else:
                        url_more = f'https://edith.xiaohongshu.com/api/sns/web/v2/comment/sub/page?note_id={note_id}&root_comment_id={root_comment_id}&image_scenes=FD_WM_WEBP,CRD_WM_WEBP&cursor={next_cursor_more}&num=10'

                    response_more = requests.get(url_more, headers=headers)
                    # print(response_more)

                    json_response_more = response_more.json()
                    # Extract expand comment
                    for comment_more in json_response_more['data']['comments']:
                        # Comment level
                        comment_level3 = '二级展开评论'
                        # 评论id
                        id3 = comment_more['id']
                        # Commenter nickname
                        nickname3 = comment_more['user_info']['nickname']
                        # Commenter id
                        user_id3 = comment_more['user_info']['user_id']
                        # Commenter home link
                        user_link3 = 'https://www.xiaohongshu.com/user/profile/' + user_id3
                        # Comment time
                        create_time3 = trans_date(comment_more['create_time'])
                        # Comment IP dependencies
                        try:
                            ip3 = comment_more['ip_location']
                        except:
                            ip3 = ''
                        # Comment likes
                        like_count3 = comment_more['like_count']
                        # comment content
                        content3 = comment_more['content']
                        print(f"评论位置：{comment_level3}, 评论时间：{create_time3}, 评论内容：{content3}")
                        data3 = {'笔记链接': note_url,
                                 '评论id': id3,
                                 '评论级别': comment_level3,
                                 '页码': page,
                                 '评论者昵称': nickname3,
                                 '评论者id': user_id3,
                                 '评论者主页链接': user_link3,
                                 '评论内容': content3,
                                 '评论时间': create_time3,
                                 '评论IP属地': ip3,
                                 '评论点赞数': like_count3
                                 }
                        # read in data
                        r.add_data(data3)

                    # Judge the second-level expansion comment termination condition
                    if not json_response_more['data']['has_more']:
                        print('**********已经获取全部的二级展开展开评论**********')
                        break
                    # If there are still expanded comments, continue the extraction loop
                    next_cursor_more = json_response_more['data']['cursor']
                    extend_page += 1
        # Determine the root comment termination condition
        if not json_response['data']['has_more']:
            print('**********已经获取全部的根评论**********')
            break
        next_cursor = json_response['data']['cursor']
        page += 1


print('**********已经获取笔记的全部评论**********')

if __name__ == '__main__':
    # 1、Set cookies for login status
    cookie = 'abRequestId=cba01750-380d-5d11-adce-dcab9948b9ce; xsecappid=xhs-pc-web; a1=18e82ebed23gnu3dweht73329yx9nee2sj8x1v0bm50000917951; webId=50abcbfd05059caf922ae727fa3620c2; acw_tc=70a4a648167b22a9784f041f03ed26f596f7f0fe5a6f10fca15655c938239091; gid=yYdYJdDi4YM8yYdYJdDdfVh8JqkI7qfEdx9WYD6qqJjvCC28jIddJy888jyWj2y8fq40Y4Jf; web_session=040069b1af95ad564a78719031344b11a5dbf7; websectiga=7750c37de43b7be9de8ed9ff8ea0e576519e8cd2157322eb972ecb429a7735d4; sec_poison_id=79e023ce-5ea4-4426-bb88-386f625c75af; webBuild=4.7.4'
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36',
        'Cookie': cookie
    }

    # 2、Set the name for saving the collected results
    # # Get the current time
    current_time = time.localtime()
    # Format current time
    formatted_time = time.strftime("%Y-%m-%d %H%M%S", current_time)
    # initialization files
    init_file_path = f'小红书笔记评论-{formatted_time}.xlsx'
    # Every 200 pieces of data are written to local excel from the cache
    r = Recorder(path=init_file_path, cache_size=200)

    # 3、Put the link to the notes you want to collect comments in a txt file
    # # The url addresses of several small red book notes are placed in the txt file, with 1 url per line
    txt_file_path = 'note_links.txt'
    # 4、Read the note link from the txt file to collect the comments
    # # note_urls are links to all notes you want to review
    note_urls = read_urls_from_txt(txt_file_path)

    # 5、Note-by-note acquisition
    for note_url in tqdm(note_urls):
        print(f"开始采集这条笔记的评论：{note_url}")
        get_comments()
        print("等待5秒后，采集下一条笔记的评论。")
        time.sleep(5)
    # 程序结束前主动写入数据，防止数据丢失
    r.record()
