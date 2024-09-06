pipeline {
    agent any

    environment {
        DB_URL = credentials('db-url')
        DB_USERNAME = credentials('db-username')
        DB_PASSWORD = credentials('db-password')
        ADDRESS = credentials('server-address')
        DOCKER_CREDENTIALS_ID = 'ec2-user'
        DOCKER_IMAGE = 'projectZeroMRSA'
        DOCKER_REGISTRY_URL = credentials('docker-registry-url')
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/RevProjects/backend.git', branch: 'main', credentialsId: "$GIT_CREDENTIALS_ID"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$BUILD_NUMBER .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([url: "$DOCKER_REGISTRY_URL", credentialsId: "$DOCKER_CREDENTIALS_ID"]) {
                    sh 'docker push $DOCKER_IMAGE:$BUILD_NUMBER'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker run -d -p 8080:8080 --env DB_URL=$DB_URL --env DB_USERNAME=$DB_USERNAME --env DB_PASSWORD=$DB_PASSWORD --env ADDRESS=$ADDRESS $DOCKER_IMAGE:$BUILD_NUMBER'
            }
        }
    }
}