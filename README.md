# jenkins-sample-spring-microservices
Jenkins CI/CD microservices pipeline

![](https://raw.githubusercontent.com/antoniopaolacci/jenkins-sample-spring-microservices/master/jenkins-pipeline.jpg)

Jenkins become very popular cause the **Jenkins pipeline**, it is a continuous delivery pipeline that executes the software workflow as code. 

The feature called **Jenkins Pipeline Project** was introduced to define the entire deployment flow of various jenkins *job* chained each others through code. What does this mean? It means that all the standard jobs defined by Jenkins are manually written as one whole script and they can be stored in a version control system. It basically follows the ‘**pipeline as code**’ discipline. Instead of building several jobs for each phase, you can now code the entire workflow and put it in a **Jenkinsfile** and can be **maintained on a SCM** (Source Code Management).



##### Jenkins workspace

The **workspace** directory is where Jenkins builds your project: it contains the source code Jenkins *checks out*, *plus any files generated by the build* itself. This **workspace** is reused for each successive build. Lets think about *multi module* java maven projects and pom *parent* properties that need to be read.



**Jenkinsfile** 

The Jenkins pipeline is written based on two syntaxes: **Declarative** pipeline vs **Scripted** pipeline

**Declarative** pipeline is a relatively new feature that supports the  pipeline as code concept. It makes the pipeline code easier to read and  write. This code is written in a Jenkinsfile which can be checked into a source control management system such as Git. Whereas, the **Scripted** pipeline is a traditional way of writing the code. In this pipeline, the Jenkinsfile is written on the Jenkins UI instance. Both these pipelines are based on the Groovy script, declarative pipeline offer a simpler and more optioned Groovy syntax.

The **declarative** pipeline is defined within a block labelled ‘**pipeline**’  whereas the **scripted** pipeline is defined within a ‘**node**’. There are various mandatory sections which are common to both the  declarative and scripted pipelines, such as stages, agent and steps that must be defined within the pipeline. 

An **Agent** is a  directive that can run multiple builds with only one instance of  Jenkins. This feature helps to distribute the workload to different  agents and execute several projects within a single Jenkins instance. It instructs Jenkins to **allocate an executor** for the builds.

A single agent can be specified for an entire pipeline or specific agents can be allotted to execute each stage within a pipeline. 

**Agent** is where the whole pipeline runs, example: *docker*; it has following parameters:

- *any*– Which mean the whole pipeline will run on any available agent.
- *none* – Which mean all the stages under the block will have to declared with agent separately.
- *label*– this is just a label for the Jenkins environment
- *docker* – this is to run the pipeline in Docker environment.

```
pipeline {
  agent { label 'node-1' }
  stages {
    stage('Source') {
      steps {
        git 'https://github.com/digitalvarys/jenkins-tutorials.git''
      }
    }
    stage('Compile') {
      tools {
        gradle 'gradle4'
      }
      steps {
        sh 'gradle clean compileJava test'
      }
    }
  }
}
```

**Scripted Pipeline:**

The scripted pipeline is a traditional way of writing the Jenkins pipeline as code. Ideally, Scripted pipeline **is written in Jenkins file on web UI of Jenkins**. The scripted pipeline provides huge control over the script and can manipulate the flow of script extensively. This helps developers to develop advance and complex pipeline as code.

Structure and syntax of the scripted pipeline:

*Node Block*: node is the part of the Jenkins architecture where Node or agent node will run the part of the workload of the jobs and master node will  handle the configuration of the job.

*Stage block* can be a single stage or multiple as the task goes. And it may have common stages like:

- Cloning the code from SCM
- Building the project
- Running the Unit Test cases
- Deploying the code
- Other functional and performance tests.

```
node ('node-1') {
  stage('Source') {
    git 'https://github.com/user-id/my-repository.git''
  }
  stage('Compile') {
    def gradle_home = tool 'gradle4'
    sh "'${gradle_home}/bin/gradle' clean compileJava test"
  }
}
```

**Declarative** type imposes limitations to the user with a more strict  and pre-defined structure, which would be ideal for simpler continuous  delivery pipelines. 

**Scripted** type has very few limitations that to with respect to  structure and syntax that tend to be defined by Groovy,thus making it  ideal for users with more complex requirements. 

You can use scripted pipeline blocks in a declarative pipeline as a workaround for complex tasks. Example of element *script*:

```
pipeline {
    agent any
    stages {
        stage('Build Docker Image') {
            steps {       
                echo 'Starting to build docker image'
                script {
                    def customImage = docker.build("my-image:${env.BUILD_ID}")
                    customImage.push()
                }   
            }
        }
    }
}
```

*Common elements*: steps and stages

**Steps**

A series of **steps** can be defined within a stage block. These steps are carried out in sequence to execute a stage. There must be at least  one step within a steps directive.

**Stages**

The **stage** block contains all the work that needs to be carried out. The work is  specified in the form of stages. There can be more than one stage within this directive. Each stage performs a specific task and have a bounded context. In the following  example, I’ve created multiple steps, each performing a specific task of single stage.

```
  ...
  stages {
    stage('Checkout') {
      steps {
        git 'https://github.com/my-user/my-repo.git''
      }
      steps {
        echo 'Running the GIT checkout phase...'
      }
     steps {
        echo 'The code is now updated on Jenkins Workspace dir...'
      }
    }
    ...
  }
  ...
```



Example of different types can be found at:

*Scripted* pipeline:

- https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/jenkins-sample-customer-service/Jenkinsfile 
- https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/jenkins-sample-discovery-service/Jenkinsfile

*Declarative* pipeline

- https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/Jenkinsfile

- https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/jenkins-sample-account-service/Jenkinsfile

**Environment variables**

Let us now have a deep look into the content of the *jenkinsfile*, we can define some environment variables  that are written in capital letters. They are environment variables which take their values by configuring them globally using Jenkins UI.

In this way, you  can build this pipeline using any private or public internet, registry and a K8s cluster on any service provider that you choose. All you have to do is to  configure these variables correctly giving them the suitable values,  thus you don’t need to touch the jenkinsfile at all.

In order to configure any environment variable using jenkins UI, follow these steps:

-  Navigate to **Manage Jenkins** > **Configure System** > **Global properties **> **Environment variables**
- Give the **Name** and the **Value** of the variable.
- Click on **Save**.

Now, let’s have a look on the different parts of the *jenkinsfile*

```
pipeline {
 
    environment {
        
        //put your own environment variables        
        REGISTRY_URI = 
   }

...
```

#### Github, Docker and Kubernetes pipeline

Install necessary jenkins plugin, docker and kubernetes. 

Navigate to **Manage Jenkins** **>** **Configure Credentials > Credentials > ****Add credentials** 

![](https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/credentials-username-password-github.jpg)

Configure your *$HOME/.kube/config* in a kind: *secret file.*

![](https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/credentials-username-password-github.jpg)

Configure your github username and password of kind: *username with password*.

Navigate to **Manage Jenkins** **>** **Configure System ** > **Configure Clouds** 

Configure docker URL and test connection.

Confifure kubernetes using kube config *secret file* and test connection.

![](https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/configure-cloud.jpg)

![](https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/docker-config.jpg)

![](https://github.com/antoniopaolacci/jenkins-sample-spring-microservices/blob/master/k8s-config.jpg)

Example of docker and kubernetes pipeline can be found on Jenkinsfile of microservices.