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
    }

    stage('Copy to Remote Server') {
            steps {
                script {
                    sshagent(credentials: ['ssh-connection-apache-server']) {
                        sh '''
                            [ -d ~/.ssh ] || mkdir ~/.ssh && chmod 0700 ~/.ssh
                            ssh-keyscan -t rsa,dsa ip-172-31-22-226 >> ~/.ssh/known_hosts
                            ssh harshana-1@ip-172-31-22-226 ...
                        '''
                    }
                }
            }
        }
}
