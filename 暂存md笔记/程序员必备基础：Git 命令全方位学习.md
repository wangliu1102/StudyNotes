**文章目录**

- Git是什么?
- Git的相关理论基础
- 日常开发中，Git的基本常用命令
- Git进阶之分支处理
- Git进阶之处理冲突
- Git进阶之撤销与回退
- Git进阶之标签tag
- Git其他一些经典命令

## Git是什么

在回忆Git是什么的话，我们先来复习这几个概念哈~

### 什么是版本控制？

百度百科定义是酱紫的~

> 版本控制是指对软件开发过程中各种程序代码、配置文件及说明文档等文件变更的管理，是软件配置管理的核心思想之一。

那些年，我们的毕业论文,其实就是版本变更的真实写照...脑洞一下，版本控制就是这些论文变更的管理~

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.5169526822820474.png)



### 什么是集中化的版本控制系统？

那么，集中化的版本控制系统又是什么呢，说白了，就是有一个集中管理的中央服务器，保存着所有文件的修改历史版本，而协同开发者通过客户端连接到这台服务器，从服务器上同步更新或上传自己的修改。



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.21955329338750706.png)



### 什么是分布式版本控制系统？

分布式版本控制系统，就是远程仓库同步所有版本信息到本地的每个用户。嘻嘻，这里分三点阐述吧：

- 用户在本地就可以查看所有的历史版本信息，但是偶尔要从远程更新一下，因为可能别的用户有文件修改提交到远程哦。
- 用户即使离线也可以本地提交，push推送到远程服务器才需要联网。
- 每个用户都保存了历史版本，所以只要有一个用户设备没问题，就可以恢复数据啦~



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.6477485330774572.png)



### 什么是Git?

Git是免费、开源的**分布式版本控制**系统，可以有效、高速地处理从很小到非常大的项目版本管理。



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.9956228508907736.png)



## Git的相关理论基础

- Git的四大工作区域
- Git的工作流程
- Git文件的四种状态
- 一张图解释Git的工作原理

### Git的四大工作区域

先复习Git的几个工作区域哈：

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.30274004699269336.png)



- **Workspace**：你电脑本地看到的文件和目录，在Git的版本控制下，构成了工作区。
- **Index/Stage**：暂存区，一般存放在 .git目录下，即.git/index,它又叫待提交更新区，用于临时存放你未提交的改动。比如，你执行git add，这些改动就添加到这个区域啦。
- **Repository**：本地仓库，你执行git clone 地址，就是把远程仓库克隆到本地仓库。它是一个存放在本地的版本库，其中**HEAD指向最新放入仓库的版本**。当你执行git commit，文件改动就到本地仓库来了~
- **Remote**：远程仓库，就是类似github，码云等网站所提供的仓库，可以理解为远程数据交换的仓库~

### Git的工作流程

上一小节介绍完Git的四大工作区域，这一小节呢，介绍Git的工作流程咯，把git的操作命令和几个工作区域结合起来，个人觉得更容易理解一些吧，哈哈，看图：



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.7042778420495156.png)

git 的正向工作流程一般就这样：



- 从远程仓库拉取文件代码回来；
- 在工作目录，增删改查文件；
- 把改动的文件放入暂存区；
- 将暂存区的文件提交本地仓库；
- 将本地仓库的文件推送到远程仓库；

### Git文件的四种状态

根据一个文件是否已加入版本控制，可以把文件状态分为：Tracked(已跟踪)和Untracked(未跟踪)，而tracked(已跟踪)又包括三种工作状态：Unmodified，Modified，Staged



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.6770347760649242.png)



- **Untracked**: 文件还没有加入到git库，还没参与版本控制，即未跟踪状态。这时候的文件，通过git add 状态，可以变为Staged状态
- **Unmodified**：文件已经加入git库, 但是呢，还没修改, 就是说版本库中的文件快照内容与文件夹中还完全一致。 Unmodified的文件如果被修改, 就会变为Modified. 如果使用git remove移出版本库, 则成为Untracked文件。
- **Modified**：文件被修改了，就进入modified状态啦，文件这个状态通过stage命令可以进入staged状态
- **staged**：暂存状态. 执行git commit则将修改同步到库中, 这时库中的文件和本地文件又变为一致, 文件为Unmodified状态.

### 一张图解释Git的工作原理



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.7302708990427151.png)



## 日常开发中，Git的基本常用命令

- git clone
- git checkout -b dev
- git add
- git commit
- git log
- git diff
- git status
- git pull/git fetch
- git push

这个图只是模拟一下git基本命令使用的大概流程哈~

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.46461293626416994.png)



### git clone

当我们要进行开发，第一步就是克隆远程版本库到本地呢

```shell
git clone url 克隆远程版本库
```

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.6043961112452918.png)



### git checkout -b dev

克隆完之后呢，开发新需求的话，我们需要新建一个开发分支，比如新建开发分支dev

创建分支：

```shell
git checkout -b dev 创建开发分支dev，并切换到该分支下
```

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.725408534679531.png)



### git add

git add的使用格式：

```shell
git add . 添加当前目录的所有文件到暂存区 
git add [dir] 添加指定目录到暂存区，包括子目录 
git add [file1] 添加指定文件到暂存区
```

有了开发分支dev之后，我们就可以开始开发啦，假设我们开发完HelloWorld.java，可以把它加到暂存区，命令如下

```shell
git add Hello.java 把HelloWorld.java文件添加到暂存区去
```

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.18496730904605574.png)



### git commit

git commit的使用格式：

```shell
git commit -m [message] 提交暂存区到仓库区,message为说明信息 
git commit [file1] -m [message] 提交暂存区的指定文件到本地仓库 
git commit --amend -m [message] 使用一次新的commit，替代上一次提交
```

把HelloWorld.java文件加到暂存区后，我们接着可以提交到本地仓库啦~

```shell
git commit -m 'helloworld开发'
```

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.5526987015562516.png)



### git status

git status,表示查看工作区状态，使用命令格式：

```shell
git status 查看当前工作区暂存区变动 
git status -s 查看当前工作区暂存区变动，概要信息 
git status --show-stash 查询工作区中是否有stash（暂存的文件）
```

当你忘记是否已把代码文件添加到暂存区或者是否提交到本地仓库，都可以用git status看看哦~

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.3087168624420353.png)



### git log

git log，这个命令用得应该比较多，表示查看提交历史/提交日志~

```shell
git log 查看提交历史 
git log --oneline 以精简模式显示查看提交历史 
git log -p <file> 查看指定文件的提交历史 
git blame <file> 一列表方式查看指定文件的提交历史
```

嘻嘻，看看dev分支上的提交历史吧~要回滚代码就经常用它喵喵提交历史~

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.7685848577458716.png)



### git diff

```shell
git diff 显示暂存区和工作区的差异 
git diff filepath filepath路径文件中，工作区与暂存区的比较差异 
git diff HEAD filepath 工作区与HEAD ( 当前工作分支)的比较差异 
git diff branchName filepath 当前分支的文件与branchName分支的文件的比较差异 
git diff commitId filepath 与某一次提交的比较差异
```

如果你想对比一下你改了哪些内容，可以用git diff对比一下文件修改差异哦

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.9546765210258072.png)



### git pull/git fetch

```shell
git pull 拉取远程仓库所有分支更新并合并到本地分支。
git pull origin master 将远程master分支合并到当前本地master分支
git pull origin master:master 将远程master分支合并到当前本地master分支，冒号后面表示本地分支

git fetch --all  拉取所有远端的最新代码
git fetch origin master 拉取远程最新master分支代码
```

我们一般都会用git pull拉取最新代码看看的，解决一下冲突，再推送代码到远程仓库的。

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.15487648233331666.png)



> 有些伙伴可能对使用git pull还是git fetch有点疑惑，其实 git pull = git fetch+ git merge。pull的话，拉取远程分支并与本地分支合并，fetch只是拉远程分支，怎么合并，可以自己再做选择。

### git push

git push 可以推送本地分支、标签到远程仓库，也可以删除远程分支哦。

```shell
git push origin master 将本地分支的更新全部推送到远程仓库master分支。
git push origin -d <branchname>   删除远程branchname分支
git push --tags 推送所有标签
```

如果我们在dev开发完，或者就想把文件推送到远程仓库，给别的伙伴看看，就可以使用git push origin dev~

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.3860054061127979.png)



## Git进阶之分支处理

Git一般都是存在多个分支的，开发分支，回归测试分支以及主干分支等，所以Git分支处理的命令也需要很熟悉的呀~

- git branch
- git checkout
- git merge

### git branch

git branch用处多多呢，比如新建分支、查看分支、删除分支等等

**新建分支：**

```shell
git checkout -b dev2 新建一个分支，并且切换到新的分支dev2 
git branch dev2 新建一个分支，但是仍停留在原来分支
```





![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.7222886826109061.png)



**查看分支：**

```shell
git branch 查看本地所有的分支 
git branch -r 查看所有远程的分支 
git branch -a 查看所有远程分支和本地分支
```

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.8378365476230992.png)



**删除分支：**

```shell
git branch -D <branchname> 删除本地branchname分支
```



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.7936648097045277.png)



### git checkout

**切换分支：**

```shell
git checkout master 切换到master分支
```

![image-20200628091408138](C:\Users\wangl\AppData\Roaming\Typora\typora-user-images\image-20200628091408138.png)



### git merge

我们在开发分支dev开发、测试完成在发布之前，我们一般需要把开发分支dev代码合并到master，所以git merge也是程序员必备的一个命令。

```shell
git merge master 在当前分支上合并master分支过来
git merge --no-ff origin/dev  在当前分支上合并远程分支dev
git merge --abort 终止本次merge，并回到merge前的状态
```

比如，你开发完需求后，发版全需要把代码合到主干master分支，如下：

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.09452685921699187.png)



## Git进阶之处理冲突

Git版本控制，还是多个人一起搞的，多个分支并存的，这就难免会有冲突出现~

### Git合并分支，冲突出现

同一个文件，在合并分支的时候，如果同一行被多个分支或者不同人都修改了，合并的时候就会出现冲突。

举个粟子吧，我们现在在dev分支，修改HelloWorld.java文件，假设修改了第三行，并且commit提交到本地仓库，修改内容如下：

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello，捡田螺的小男孩！");
    }
}
```

我们切回到master分支，也修改HelloWorld.java同一位置内容，如下：

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello，jay！！");
    }
}
```

再然后呢，我们提交一下master分支的这个改动，并把dev分支合并过下，就出现冲突啦，如图所示：



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.2534139131007906.png)



### Git解决冲突

Git 解决冲突步骤如下：

- 查看冲突文件内容
- 确定冲突内容保留哪些部分，修改文件
- 重新提交，done

#### 1.查看冲突文件内容

git merge提示冲突后，我们切换到对应文件，看看冲突内容哈，，如下：

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.4216354226016104.png)



#### 2.确定冲突内容保留哪些部分，修改文件

- Git用<<<<<<<，=======，>>>>>>>标记出不同分支的内容，
- <<<<<<<HEAD是指主分支修改的内容，>>>>>>> dev是指dev分支上修改的内容

所以呢，我们确定到底保留哪个分支内容，还是两个分支内容都保留呢，然后再去修改文件冲突内容~

#### 3.修改完冲突文件内容，我们重新提交，冲突done



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.17944472651891033.png)



## Git进阶之撤销与回退

Git的撤销与回退，在日常工作中使用的比较频繁。比如我们想将某个修改后的文件撤销到上一个版本，或者想撤销某次多余的提交，都要用到git的撤销和回退操作。

代码在Git的每个工作区域都是用哪些命令撤销或者回退的呢，如下图所示：

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.16010476840742086.png)



有关于Git的撤销与回退，一般就以下几个核心命令

- git checkout
- git reset
- git revert

### git checkout

如果文件还在**工作区**，还没添加到暂存区，可以使用git checkout撤销

```shell
git checkout [file] 丢弃某个文件file 
git checkout . 丢弃所有文件
```

以下demo，使用git checkout -- test.txt 撤销了暂存区test.txt的修改

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.19903122034608062.png)



### git reset

#### git reset的理解

> git reset的作用是修改HEAD的位置，即将HEAD指向的位置改变为之前存在的某个版本.

为了更好地理解git reset，我们来回顾一下,Git的版本管理及HEAD的理解

> Git的所有提交，会连成一条时间轴线，这就是分支。如果当前分支是master，HEAD指针一般指向当前分支，如下：



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.4214621866699102.png)



假设执行git reset，回退到版本二之后，版本三不见了哦,如下：



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.5894770805431636.png)



#### git reset的使用

Git Reset的几种使用模式

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.37988258398903135.png)



```shell
git reset HEAD --file 回退暂存区里的某个文件，回退到当前版本工作区状态 
git reset –-soft 目标版本号 可以把版本库上的提交回退到暂存区，修改记录保留 
git reset –-mixed 目标版本号 可以把版本库上的提交回退到工作区，修改记录保留 
git reset –-hard 可以把版本库上的提交彻底回退，修改的记录全部revert。
```

先看一个粟子demo吧，代码git add到暂存区，并未commit提交,就以下酱紫回退，如下：

```shell
git reset HEAD file 取消暂存 
git checkout file 撤销修改
```

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.01997098597485875.png)



再看另外一个粟子吧，代码已经git commit了，但是还没有push：

```shell
git log 获取到想要回退的commit_id 
git reset --hard commit_id 想回到过去，回到过去的commit_id
```

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.8079974121374932.png)



如果代码已经push到远程仓库了呢，也可以使用reset回滚哦(这里大家可以自己操作实践一下哦)~

```shell
git log 
git reset --hard commit_id 
git push origin HEAD --force
```

### git revert

> 与git reset不同的是，revert复制了那个想要回退到的历史版本，将它加在当前分支的最前端。

**revert之前：**

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.5563032316132411.png)

**revert 之后：**

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.8186263924557614.png)



当然，如果代码已经推送到远程的话，还可以考虑revert回滚呢

```shell
git log 得到你需要回退一次提交的commit id
git revert -n <commit_id>  撤销指定的版本，撤销也会作为一次提交进行保存
```



## Git进阶之标签tag

打tag就是对发布的版本标注一个版本号，如果版本发布有问题，就把该版本拉取出来，修复bug，再合回去。

```shell
git tag 列出所有tag
git tag [tag] 新建一个tag在当前commit
git tag [tag] [commit] 新建一个tag在指定commit
git tag -d [tag] 删除本地tag
git push origin [tag] 推送tag到远程
git show [tag] 查看tag
git checkout -b [branch] [tag] 新建一个分支，指向某个tag
```



## Git其他一些经典命令

### git rebase

rebase又称为衍合，是合并的另外一种选择。

假设有两个分支master和test

```shell
      D---E test
      /
 A---B---C---F--- master
执行 git merge test得到的结果
```

 

```shell
       D--------E
      /          \
 A---B---C---F----G---   test, master
执行git rebase test，得到的结果
```

```shell
A---B---D---E---C‘---F‘---   test, master
```

**rebase好处是：** 获得更优雅的提交树，可以线性的看到每一次提交，并且没有增加提交节点。所以很多时候，看到有些伙伴都是这个命令拉代码：git pull --rebase

### git stash

stash命令可用于临时保存和恢复修改

```shell
git stash 把当前的工作隐藏起来 等以后恢复现场后继续工作
git stash list 显示保存的工作进度列表
git stash pop stash@{num} 恢复工作进度到工作区
git stash show ：显示做了哪些改动
git stash drop stash@{num} ：删除一条保存的工作进度
git stash clear 删除所有缓存的stash。

```





### git reflog

显示当前分支的最近几次提交



![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.9693528872748481.png)



### git blame filepath

git blame 记录了某个文件的更改历史和更改人，可以查看背锅人，哈哈

![img](file:///D:\Documents\My Knowledge\temp\b60fbcfb-4ecf-4e9b-8c8c-f626360cd6de\128\index_files\0.9563454754508735.png)



### git remote

```shell
git remote 查看关联的远程仓库的名称 
git remote add url 添加一个远程仓库
git remote show [remote] 显示某个远程仓库的信息
```


作者：Jay_huaxiao
链接：https://juejin.im/post/5eadfa065188256d703f3afa