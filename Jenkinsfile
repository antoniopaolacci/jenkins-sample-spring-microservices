pipeline {
  agent any
  triggers {
     pollSCM '* * * * *'
  }
  stages {
	stage ('Initialize') {
      steps {
        echo 'Init step.'
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