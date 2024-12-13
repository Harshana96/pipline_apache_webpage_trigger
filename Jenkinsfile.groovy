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

        stage('Get Caller Identity') {
            steps {
                script {
                    // Run AWS CLI command to get the caller identity and print the details
                    echo 'Getting AWS Caller Identity...'
                    sh '''
                        echo "Caller Identity:"
                        aws sts get-caller-identity --query "Arn" --output text
                    '''
                }
            }
        }

        stage('List S3 Buckets') {
            steps {
                script {
                    // List all S3 buckets in the AWS account
                    echo 'Listing S3 Buckets...'
                    sh '''
                        aws s3api list-buckets --query "Buckets[].Name" --output text
                    '''
                }
            }
        }
    }
}
