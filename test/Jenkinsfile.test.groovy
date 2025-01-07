pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    def testResults = jenkinsPipelineUnit.runTests()
                    junit testResults
                }
            }
        }
        stage('Report Results') {
            steps {
                echo 'Test results reported.'
            }
        }
    }
}



