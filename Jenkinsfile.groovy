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
            // Ensure the correct private key is used by ssh-agent
            sshagent(credentials: ['ssh-connection-apache-server']) {
                sh '''#!/bin/bash
                    # Step 1: Ensure ~/.ssh exists and has correct permissions
                    [ -d ~/.ssh ] || mkdir -p ~/.ssh
                    chmod 0700 ~/.ssh

                    # Step 2: Add the server to known_hosts (disable host verification warning)
                    ssh-keyscan -t rsa,dsa ip-172-31-22-226 >> ~/.ssh/known_hosts

                    # Step 3: Verify that the key has been added correctly (optional for debugging)
                    cat ~/.ssh/known_hosts

                    # Step 4: Use SSH to log in and execute a command
                    ssh -o StrictHostKeyChecking=no harshana-1@ip-172-31-22-226 "echo 'Successfully connected to the server!'"
                '''
            }
        }
    }
}

}
