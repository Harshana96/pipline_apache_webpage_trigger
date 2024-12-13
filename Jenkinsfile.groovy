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

    stages {
        stage('List S3 Buckets') {
            steps {
                script {
                    // AWS CLI command to list all S3 buckets using direct access keys
                    sh '''
                        aws s3api list-buckets --query "Buckets[].Name" 
                    '''
                }
            }
        }
    }

    
}
