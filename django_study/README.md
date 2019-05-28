https://www.djangoproject.com/start/

https://docs.djangoproject.com/zh-hans/2.0/

```
django_study
│
├─django_study
├─polls
│  ├─migrations
│  ├─static
│  │  └─polls
│  │      └─images
│  ├─templates
│  │  └─polls
└─templates
    └─admin
```

## 指令notebook

```bash
# 查看django版本
python -m django --version
# 创建一个项目
django-admin startproject [project]
# 在0.0.0.0:8080上运行服务器
python manage.py runserver 0:8000
# 对项目启动python console
python manage.py shell
# 创建admin中的超级用户
python manage.py createsuperuser
# 在项目中创建app
python manage.py startapp [app]
# 在app中创建/修改模型
python manage.py makemigrations [app]
# 使app的模型生效，在数据库中创建对应数据表
python manage.py migrate
# 查看``migrate的SQL代码
python manage.py sqlmigrate [app] [name]

```