pipeline {
    agent any

    environment {
        ANSIBLE_HOST_KEY_CHECKING = 'False'
    }
    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                git 'https://github.com/karthiksaki/ci-cd-project.git'
            }
        }
        stage('Install Ansible') {
            steps {
                // Install Ansible
                sh 'sudo yum update'
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
