pipeline {
    agent any
    // options {
    //     // Keep the last 5 logs
    //     buildDiscarder(logRotator(numToKeepStr: '5'))
    // }
    environment {
        ANSIBLE_HOST_KEY_CHECKING = 'False'
    }
    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository using credentials
                git url: 'https://github.com/karthiksaki/ci-cd-project.git', branch: 'main', credentialsId: 'your-credentials-id'
            }
        }
        stage('Install Ansible') {
            steps {
                // Install Ansible
                sh 'sudo yum update -y'
                sh 'sudo yum install -y ansible'
            }
        }
        stage('Parallel Execution') {
            parallel {
                stage('Setup Tomcat Server') {
                    steps {
                        // Run Ansible playbook to install and configure Tomcat server
                        ansiblePlaybook playbook: 'ansible/tomcat-setup.yml', inventory: 'ansible/hosts'
                    }
                }
                stage('Install Web Server') {
                    steps {
                        // Run Ansible playbook to install web server with HTTP and HTTPS
                        ansiblePlaybook playbook: 'ansible/webserver-setup.yml', inventory: 'ansible/hosts'
                    }
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
