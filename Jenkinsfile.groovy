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

        stage('Create Folder in S3 and Upload index.html') {
            steps {
                script {
                    // Get the current date and time formatted as YYYY-MM-DD-HHmmss
                    def currentDateTime = new Date().format('yyyy-MM-dd-HHmmss', TimeZone.getTimeZone('UTC'))
                    echo "Creating folder with name: ${currentDateTime}"

                    // Specify the S3 bucket name (replace with your actual bucket name)
                    def bucketName = 'hash2buket'

                    // Create the folder and upload index.html file to the folder in the S3 bucket
                    sh """
                        aws s3 cp index.html s3://${bucketName}/folders/${currentDateTime}/index.html
                    """
                }
            }
        }

        stage('List Folders in S3') {
            steps {
                script {
                    // Specify the S3 bucket name (replace with your actual bucket name)
                    def bucketName = 'hash2buket'

                    // List all folders in the specified S3 bucket
                    echo 'Listing folders in S3 bucket...'
                    sh """
                        aws s3 ls s3://${bucketName}/ --recursive | grep 'PRE' | awk '{print \$2}'
                    """
                }
            }
        }
    }
}
