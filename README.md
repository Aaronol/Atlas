# Atlas
基于知识图谱的农业病虫害数据查询系统(附数据爬虫)
# d2rq启动流程
- 根据数据库表生成对应映射文件
generate-mapping -u 用户名 -p 密码 -o 映射文件名 数据库连接信息
generate-mapping -u root -p 123456 -o crop_atlas.ttl jdbc:mysql:///kg_demo_movie

- D2RQ由rdf映射文件生成实体rdf
.\dump-rdf.bat -o crop_atlas.nt .\crop_atlas.ttl

- D2RQ服务器启动
d2r-server.bat crop_pedigree.ttl

- 农业病虫害信息云数据库
http://cloud.sinoverse.cn/index_bch.htmlw
