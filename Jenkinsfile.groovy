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
                    sshagent(['ssh-connection-apache-server']) {
                       
                    }
                }
            }
        }
}
