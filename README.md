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
# 页面展示
![QQ图片20210530202058](https://user-images.githubusercontent.com/35592681/120103960-c6cb7b00-c184-11eb-993b-9de715d13e9f.png)
![QQ图片20210530202135](https://user-images.githubusercontent.com/35592681/120103962-c7641180-c184-11eb-8290-2d63d5c74bcf.png)
![QQ图片20210530202152](https://user-images.githubusercontent.com/35592681/120103966-cb902f00-c184-11eb-9582-b91cd4549bb5.png)
![QQ图片20210530202157](https://user-images.githubusercontent.com/35592681/120103967-cc28c580-c184-11eb-9c94-7d2f7cb5f521.png)
![QQ图片20210530202201](https://user-images.githubusercontent.com/35592681/120103970-cd59f280-c184-11eb-86c7-df2872d4ab06.png)
![QQ图片20210530202203](https://user-images.githubusercontent.com/35592681/120103975-cf23b600-c184-11eb-9549-b4a7b818be08.png)
![QQ图片20210530202206](https://user-images.githubusercontent.com/35592681/120103978-d0ed7980-c184-11eb-84c0-15936b73cfe4.png)


