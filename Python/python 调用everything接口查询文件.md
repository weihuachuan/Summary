# python 调用everything接口查询文件
```python 

    def search(search=''):
        params = {
            'search': search,  # 搜索文本
            'offset': '0',
            'json': '1',
            'count': '1', # 数值非0限制搜索条数
            'case': '0',  # 数值非零时匹配大小写
            'wholeword': '0',  # 数值非零时匹配全字
            'path': '0',  # 数值非零时搜索完整路径
            'diacritics': '0',  # 数值非零时匹配变音标记
            'path_column': '1',  # 数值非零时列举结果路径为 json 对象
            'size_column': '1',  # 数值非零时列举结果大小为 json 对象
            'date_modified_column': '1',  # 数值非零时列举结果修改日期为 json 对象
            'date_created_column': '0',  # 数值非零时列举结果创建日期为 json 对象
            'attributes_column': '0',  # 数值非零时列举结果属性为 json 对象
            'sort': 'size',  # name,path,date_modified,size
            'ascending': '0',  # 数值非零时升序排列
        }
        r = requests.get('http://localhost:9000/', params=params, timeout=8)
        ret_data = r.json()
        total = ret_data['totalResults']
        results = ret_data['results']
        return results
if __name__ == '__main__':
    # 返回格式字典数组
    result = search(search='魏华川')
    print(result)        
```