from setuptools import setup

setup(
    name='django_study',
    version='1.0.0',
    packages=['', 'polls'],
    package_dir={'': 'django_study',
                 'polls': 'polls'},
    url='github.com/spencercjh/leetcode',
    license='Apache License',
    author='spencercjh',
    author_email='shouspencercjh@foxmail.com',
    description='offical demo', install_requires=['django']
)
