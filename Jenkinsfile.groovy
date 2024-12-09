pipeline {
    agent any

    stages {
        stage('Check Local File') {
            steps {
                script {
                    sh '''
                        echo "+++++++++Current directory:+++++++++"
                        pwd
                        echo "+++++++++Listing files:+++++++++"
                        ls -l
                        echo "+++++++++Displaying index.html content:+++++++++"
                        cat index.html
                    '''
                }
            }
        }

        stage('Copy to Remote Server') {
            steps {
                script {
                    sshagent(['ssh-connection-apache-server']) {
                        sh '''
                            echo "+++++++++Copying index.html to remote server+++++++++"
                            scp -o StrictHostKeyChecking=no -i /var/lib/jenkins/workspace/test_pipline_1@tmp/private_key_1699415439007742700.key index.html hashj@172-31-22-226:/tmp/index.html
                            echo "+++++++++Taking root access and moving file+++++++++"
                            ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/workspace/test_pipline_1@tmp/private_key_1699415439007742700.key hashj@172-31-22-226 \
                                'sudo -i bash -c "mv /tmp/index.html /var/www/html/index.html && systemctl reload apache2.service"'
                        '''
                    }
                }
            }
        }
    }
}
