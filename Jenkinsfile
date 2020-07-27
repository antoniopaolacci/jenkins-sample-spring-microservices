pipeline {
  agent any
  triggers {
     pollSCM '* * * * *'
  }
  stages {
	stage ('Initialize') {
      steps {
        echo 'Placeholder.'
      }
    }
    stage ('Checking java version') {
      steps {
         sh 'java -version'
       }
     }
    stage ('maven version') {
      steps {               
          sh 'mvn -version'                
       }
     }
	stage('Build') {
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