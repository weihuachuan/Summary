# python文件处理
## FileUtil

```python
import gzip
import re
import shutil
import zipfile
import requests
from config.application import *
from utils.FilePathUtil import FilePathUtil
from utils.log import Log

class FileUtil(object):

    @staticmethod
    def download_file(file_path, down_path, log):
        down_url = DOWNLOAD_URL
        write_path = os.path.join(down_path, os.path.basename(file_path))
        down_data = {
                     "file_path": file_path,
                     }
        try:
            down_res = requests.get(url=down_url, params=down_data)
            with open(write_path, "wb") as code:
                code.write(down_res.content)
            print(file_path + ' 文件下载成功')
            log.info(file_path + ' 文件下载成功')
        except Exception as e:
            log.error(file_path + '文件下载失败' + str(e))
            print(file_path + '文件下载失败' + str(e))

    @staticmethod
    def zip_file(base_path):
        files = os.listdir(base_path)
        zp = zipfile.ZipFile(base_path + os.sep + 'log.zip', 'a', zipfile.ZIP_STORED)
        for file in files:
            file_path = base_path + os.sep + file
            zp.write(file_path, arcname=file)
        zp.close()


    @staticmethod
    def upload_file(file_path, task_id, log):
        try:
            '''压缩文件'''
            FileUtil.zip_file(file_path)
            files = {'log_file': open(file_path + os.sep + 'log.zip', 'rb').read()}
            res = requests.post(UPLOAD_URL, files=files, data={'task_id': task_id})
            log.info("日志上传成功")
            print("日志上传成功")
        except Exception as e:
            log.error('日志上传失败' + str(e))
            print('日志上传失败' + str(e))

    @staticmethod
    def delete_dir(base_dir, log):
        """
        删除文件夹及里面的文件
        :return:
        """
        if os.path.isdir(base_dir) and os.path.exists(base_dir):
            try:
                shutil.rmtree(base_dir)
                log.info('清理文件成功')
                return True
            except Exception as e:
                log.error('删除文件夹失败======>' + str(e))
                print('删除文件夹失败======>' + str(e))
                return False
        else:
            log.error('删除文件夹失败======>' + base_dir + '不是一个文件夹')
            print('删除文件夹失败======>' + base_dir + '不是一个文件夹')
            return False

    @staticmethod
    def copy_to_path(old_path, new_path, log):
        """
        复制文件夹内容到新的文件夹中
        :param old_path: 旧的路径
        :param new_path: 新的文件夹路径
        :return:
        """
        try:
            shutil.copytree(old_path, new_path)
            log.info('文件添加到输出路径成功')
            return True
        except Exception as e:
            # log.error('文件添加到输出路径失败======>' + str(e))
            # print('复制文件夹失败======>' + str(e))
            return False

    @staticmethod
    def copy_file_to_path(file_path, new_path, log):
        """
        复制文件到新的文件夹中
        :param file_path: 文件路径
        :param new_path: 新的文件夹路径
        :return:
        """
        try:
            shutil.copy(file_path, new_path)
            log.info('文件添加到输出路径成功')
            return True
        except Exception as e:
            log.error('文件添加到输出路径失败======>' + str(e))
            print('复制文件夹失败======>' + str(e))
            return False

    @staticmethod
    def un_all_gz(download_path: str):
        """
            解压所有gz文件，并删除gz文件
        """
        file_list = os.listdir(download_path)
        for file_name in file_list:
            if file_name.endswith(".gz"):
                abspath = download_path + os.sep + file_name
                FileUtil.un_gz(abspath)
                os.remove(abspath)

    @staticmethod
    def un_gz(file_name: str):
        """
        解压单个gz文件
        """
        # 获取文件的名称，去掉后缀名
        f_name = file_name.replace(".gz", "")
        # 开始解压
        g_file = gzip.GzipFile(file_name)
        # 读取解压后的文件，并写入去掉后缀名的同名文件（即得到解压后的文件）
        open(f_name, "wb+").write(g_file.read())
        g_file.close()

    @staticmethod
    def rename_csv_file(path: str):
        """
        更改文件夹内所有csv文件名称到<=18字符。(以支持DailyReport)
        支持改名的文件格式: xx-Export-ID-xx.csv
        """
        file_list = os.listdir(path)
        rename_list = {}
        for file in file_list:
            if os.path.isdir(path + os.sep + file):
                FileUtil.rename_csv_file(path + os.sep + file)
            else:
                new_name = file
                result = re.search("(?<=^Auto ).*", new_name)  # 以Auto开头,去掉 Auto
                if result:
                    new_name = result.group()

                delete_str = re.search("-Export-ID.*(?=.csv$)", new_name)
                if delete_str:
                    name = new_name.replace(delete_str.group(), "")
                    if name not in rename_list:
                        rename_list[name] = 0
                    else:
                        rename_list[name] += 1
                    new_name = new_name.replace(delete_str.group(), " " + str(rename_list[name]))
                    os.rename(path + os.sep + file, path + os.sep + new_name)

    @staticmethod
    def file_isExists(path):
        flag = os.path.exists(path)
        return flag


    @staticmethod
    def remove_files(path: str, file_and_type_dict: dict):
        """
        将类型相同的文件移动到同个文件夹.如A，将A-Export-ID.*划分为一类
        @Param: path 操作的文件目录
        @Param: file_and_type_dict文件名与其类型的字典 key为A-Export-ID*格式，例如 {
            "A" : "D10",
            "B" : "D11"
        }
        """
        files = os.listdir(path)        # 真实文件名
        for re_name, type_name in file_and_type_dict.items():
            for file in files:
                if re.match("^" + re_name + "-Export-ID.*", file):
                    shutil.move('\\\?\\' + os.path.join(path, file), '\\\?\\' + os.path.join(os.path.join(path, type_name + ' sampling'), 'rawdata'))
                    break

if __name__ == '__main__':
    # 文件输出路径
    task_out_path = FilePathUtil.task_output_path('teset')
    # 使用方法2
    log = Log(FilePathUtil.log_path(task_out_path))
    # FileUtil.download_file(r'D:\COEX\Bronco PVTE\report_90\Report\sfiles.sql', FilePathUtil.download_path('task1', 'report1', 'report'))
    # FileUtil.upload_file(r'D:\Coex_Data\Coex_Auto_Export\2022-08-26\coex_auto\coex_auto Coex Daily Report 2022-08-26_17-06\log', '48', log)
    # FileUtil.zip_file(r'D:\Coex_Data\Coex_Auto_Export\2022-08-26\coex_auto\coex_auto Coex Daily Report 2022-08-26_17-06\log')
    # FileUtil.copy_to_path(r"D:\Coex_Data\Coex_Auto_Export\2022-08-26\coex_auto\coex_auto Coex Daily Report 2022-08-26_17-06\html_json.json", r'D:\Coex_Data\Coex_Auto_Export\2022-08-26\coex_auto\coex_auto Coex Daily Report 2022-08-26_17-06\log',log)
    shutil.copy(r"D:\Coex_Data\Coex_Auto_Export\2022-08-26\coex_auto\coex_auto Coex Daily Report 2022-08-26_17-06\html_json.json", r'D:\Coex_Data\Coex_Auto_Export\2022-08-26\coex_auto\coex_auto Coex Daily Report 2022-08-26_17-06\log')
```