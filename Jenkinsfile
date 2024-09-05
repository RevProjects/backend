pipeline {
    agent any

    environment {
        DB_URL = credentials('db-url')
        DB_USERNAME = credentials('db-username')
        DB_PASSWORD = credentials('db-password')
        ADDRESS = credentials('server-address')
        DOCKER_CREDENTIALS_ID = 'docker-credentials-id'
        DOCKER_IMAGE = 'your-docker-repo/projectZeroMRSA'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-repo/projectZeroMRSA.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_IMAGE:$BUILD_NUMBER .'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withDockerRegistry([credentialsId: "$DOCKER_CREDENTIALS_ID"]) {
                        sh 'docker push $DOCKER_IMAGE:$BUILD_NUMBER'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'docker run -d -p 8080:8080 $DOCKER_IMAGE:$BUILD_NUMBER'
                }
            }
        }
    }
}