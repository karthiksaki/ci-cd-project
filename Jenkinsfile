pipeline {
    agent any

    environment {
        ANSIBLE_HOST_KEY_CHECKING = 'False'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository using credentials
                git url: 'https://github.com/karthiksaki/ci-cd-project.git',  branch: 'main', credentialsId: '1cd527b0-6b3d-4351-b5b0-be007b002162'
            }
        }

        stage('Install Ansible') {
            steps {
                // Install Ansible
                sh 'sudo yum update -y'
                sh 'sudo yum install -y ansible'
            }
        }

        stage('Setup Tomcat Server') {
            steps {
                // Run Ansible playbook to install and configure Tomcat server
                ansiblePlaybook playbook: 'ansible/tomcat-setup.yml'
            }
        }

        stage('Install Web Server') {
            steps {
                // Run Ansible playbook to install web server with HTTP and HTTPS
                ansiblePlaybook playbook: 'ansible/webserver-setup.yml'
            }
        }

        stage('Run Groovy Script') {
            steps {
                // Run the Groovy script
                script {
                    def groovyScript = readFile 'day-02/first.groovy'
                    evaluate(groovyScript)
                }
            }
        }
    }

    post {
        always {
            // Archive the artifacts
            archiveArtifacts artifacts: '**/ansible/*.yml', allowEmptyArchive: true
        }
        success {
            // Notify success
            echo 'Pipeline succeeded!'
        }
        failure {
            // Notify failure
            echo 'Pipeline failed!'
        }
    }
}
