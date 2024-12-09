pipeline {
    agent any

    environment {
        REMOTE_SERVER = 'ubuntu@ip-172-31-22-226'
        PRIVATE_KEY_PATH = '~/.ssh/id_rsa'
    }

    stages {
        stage('Check Local File') {
            steps {
                script {
                    sshagent(credentials: ['your-ssh-credentials-id']) {
                        // Copy the file
                        sh '''
                            echo "+++++++++Copying index.html to remote server+++++++++"
                            scp -o StrictHostKeyChecking=no -i ${PRIVATE_KEY_PATH} index.html ${REMOTE_SERVER}:/tmp/index.html
                        '''
                        // SSH and move file
                        sh '''
                            echo "+++++++++Taking root access and moving file+++++++++"
                            ssh -o StrictHostKeyChecking=no -i ${PRIVATE_KEY_PATH} ${REMOTE_SERVER} \
                                'sudo mv /tmp/index.html /var/www/html/index.html && sudo systemctl reload apache2.service'
                        '''
                    }
                }
            }
        }
    }

    stage('Copy to Remote Server') {
            steps {
                script {
                    sshagent(['ssh-connection-apache-server']) {
                       
                    }
                }
            }
        }
}
