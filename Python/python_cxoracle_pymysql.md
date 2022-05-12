# python 操纵数据库

```python
#  coding=utf-8
import cx_Oracle
import pymysql
import json
import datetime as dt
import uuid,os
 
 
os.environ['NLS_LANG']='AMERICAN_AMERICA.ZHS16GBK'
class OracleDB(object):
    def  __init__(self,user,pwd,ip,port,sid):
        self.connect=cx_Oracle.connect(user,pwd,ip+":"+port+"/"+sid)
        self.cursor=self.connect.cursor()
 
    """处理数据二维数组，转换为json数据返回"""
    def select(self,sql):
        list=[]
        self.cursor.execute(sql)
        result=self.cursor.fetchall()
        col_name=self.cursor.description
        for row in result:
            dict={}
            for col in range(len(col_name)):
                key=col_name[col][0]
                value=row[col]
                dict[key]=value
            list.append(dict)
        js=json.dumps(list,ensure_ascii=False,indent=2,separators=(',',':'))
        return js
 
    def disconnect(self):
        self.cursor.close()
        self.connect.close()
 
    def insert(self,sql,list_param):
        try:
            self.cursor.executemany(sql,list_param)
            self.connect.commit()
            print("insert success")
        except Exception as e:
            print(e)
        finally:
            self.disconnect()
   
    def update(self, sql):
        try:
            self.cursor.execute(sql)
            self.connect.commit()

        except Exception as e:
            print(e)
        finally:
            self.disconnect()

    def delete(self, sql):
        try:
            self.cursor.execute(sql)
            self.connect.commit()
            print("delete ok")
        except Exception as e:
            print(e)
        finally:
            self.disconnect()
 
class mysqlDB(object):
    def  __init__(self,user,pwd,ip,port):
        connect = pymysql.connect(host=ip, port=port, user=user, passwd=pwd, db='mysql',
                               charset='utf8')
        self.connect=connect
        self.cursor=self.connect.cursor(cursor=pymysql.cursors.DictCursor)
 
 
    def _format_data(self,rows):
        DOMAIN = '023'
        res = []
        cols = ['ID', 'POINT_ID', 'DOMAIN', 'RESOURCEID', 'RESOURCENAME', 'RESOURCETYPE', 'TABLENAME', 'DATASOURCE',
                'SOURCENAME', 'STATTYPE', 'STAT_PERIOD', 'REC_COUNT', 'REC_STORE', 'OUT_COUNT', 'FAILED_COUNT',
                'STATUS_UPDATATIME','BEGINTIME', 'ENDTIME', 'BUSI_BEGINTIME', 'BUSI_ENDTIME', 'MODEL_NAME']
 
        for row in rows:
            tmp = []
            row['ID'] = str(uuid.uuid1())
            row['POINT_ID'] = DOMAIN + row['source_id']
            row['DOMAIN'] = DOMAIN
            row['RESOURCEID'] = row['source_id']
            row['RESOURCENAME'] = u'通联明细'
            row['RESOURCETYPE'] = u'通联明细'
            row['TABLENAME'] = 'EDGE_GROUPCALL_DETAIL' if row['source_id'] == 'BBD_CALL_INFO' else 'EDGE_GROUPMSG_DETAIL'
            row['DATASOURCE'] = 'ZT'
            row['SOURCENAME'] = 'TB_LTECDR'
            row['STATTYPE'] = 'INCR'
            row['STAT_PERIOD'] = ''
            row['REC_COUNT'] = row['rec_count']
            row['OUT_COUNT'] = row['out_count']
            row['FAILED_COUNT'] = 0
            row['STATUS_UPDATATIME'] = dt.datetime.now()
            row['BEGINTIME'] = row['compute_time']
            row['ENDTIME'] = row['compute_time'] + dt.timedelta(hours=1)
            row['BUSI_BEGINTIME'] = row['compute_time'].strftime('%Y:%m:%d 00:00:00')
            row['BUSI_ENDTIME'] = (row['compute_time'] + dt.timedelta(hours=1)).strftime('%Y:%m:%d 00:00:00')
            row['MODEL_NAME'] = 'LOAD_HIVE_BBD'
            for key in cols:
                tmp.append(row.get(key))
            res.append(tmp)
        return res
 
 
    """ 处理存储的原始数据,整理成汇智需要的格式"""
    def select_monitor_source(self):
        sql = u''' select source_id,compute_time,rec_count,out_count from pangu.tb_monitor_count where sync = 0 '''
        self.cursor.execute(sql)
        rows = self.cursor.fetchall()
        return self._format_data(rows)
 
 
    def disconnect(self):
        self.cursor.close()
        self.connect.close()
 
    def update_monitor_sync(self):
        try:
            sql = ''' update pangu.tb_monitor_count set sync = 1 where sync = 0 '''
            self.cursor.execute(sql)
            self.connect.commit()
        except Exception as e:
            self.connect.rollback()
            print(e)
        finally:
            self.disconnect()
            
 
def sync_monitor():
    bbd_oracle = OracleDB('bbd', 'bbd', '12.4.0.68', '1521', 'zyk')
    mysqldb = mysqlDB('bbdweb', 'bbdweb@lDiDE5LB', '12.68.1.32', 3306)
    cols = ['ID', 'POINT_ID', 'DOMAIN', 'RESOURCEID', 'RESOURCENAME', 'RESOURCETYPE', 'TABLENAME', 'DATASOURCE',
            'SOURCENAME',
            'STATTYPE', 'STAT_PERIOD', 'REC_COUNT', 'REC_STORE', 'OUT_COUNT', 'FAILED_COUNT', 'STATUS_UPDATATIME',
            'BEGINTIME',
            'ENDTIME', 'BUSI_BEGINTIME', 'BUSI_ENDTIME', 'MODEL_NAME']
    cols_expr = ','.join(cols)
    vals_expr = ':' + ',:'.join(cols)
    sql = ''' insert into BBD.TB_MONITOR_INCR (%s) values (%s) ''' % (cols_expr, vals_expr)
    rows = mysqldb.select_monitor_source()
    bbd_oracle.insert(sql, rows)
    mysqldb.update_monitor_sync()
    bbd_oracle.disconnect()
    mysqldb.disconnect()
 
 
if __name__ =="__main__":
    sync_monitor()
    
# if __name__ == "__main__":
#     test_oracle = TestOracle(‘SCOTT‘, ‘pipeline‘, ‘127.0.0.1‘, ‘1521‘, ‘orcl‘)
#     param = [(‘ww1‘, ‘job003‘, 1333, 2), (‘ss1‘, ‘job004‘, 1444, 2)]
#     # test_oracle.insert("insert into bonus(ENAME,JOB,SAL,COMM)values(:1,:2,:3,:4)",param)#也可以下面这样解决orc-1036非法变量问题
#     test_oracle.insert("insert into bonus(ENAME,JOB,SAL,COMM)values (:ENAME,:JOB,:SAL,:COMM)", param)
#     test_oracle1 = TestOracle(‘SCOTT‘, ‘pipeline‘, ‘127.0.0.1‘, ‘1521‘, ‘orcl‘)
#     test_oracle1.delete("delete from bonus where ENAME=‘ss1‘ or ENAME=‘ww1‘")
#     test_oracle3 = TestOracle(‘SCOTT‘, ‘pipeline‘, ‘127.0.0.1‘, ‘1521‘, ‘orcl‘)
#     js = test_oracle3.select(‘select * from bonus‘)
#     print(js)    
 
```

