pipeline {

  agent any
  
  triggers {
     pollSCM '* * * * *'
  }
  
  environment {
     myVersion='2.0.3' 
  }
  
  stages {
	stage ('Initialize') {
      steps {
        echo "Init step. CI/CD version: ${env.myVersion}"
      }
    }
    stage ('Checking java version') {
      steps {
         sh 'java -version'
       }
     }
    stage ('Checking maven version') {
      steps {               
          sh 'mvn -version'                
       }
     }
	stage('Build without test') {
	  steps {
         sh 'mvn clean install -DskipTests'
      }
    }   
    stage('Test') {
	  steps {
         sh 'mvn test'
      }
    }
  }
  
}