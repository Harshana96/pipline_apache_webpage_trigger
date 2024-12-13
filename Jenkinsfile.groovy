pipeline {
    agent any
    environment {
        AWS_REGION = 'us-east-1' // e.g., 'us-east-1'
        BUCKET_NAME = 'hash2buket'
        FOLDER_NAME = 'example-folder/' // Include the trailing slash to denote a folder
    }

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
        stage('Create Folder in S3') {
            steps {
                script {
                    // Ensure AWS CLI is configured in the Jenkins environment
                    sh """
                        aws s3api put-object --bucket $BUCKET_NAME --key $FOLDER_NAME --region $AWS_REGION
                    """
                }
            }
        }
    }
}
