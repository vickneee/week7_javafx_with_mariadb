pipeline {

    agent any

    tools{
        maven 'Maven3'
    }

    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKER_IMAGE = 'vickneee/javafx_with_mariadb'
        DOCKER_TAG = 'latest'
    }

    stages {
//         stage('Setup Maven') {
//             steps {
//                 script {
//                     def mvnHome = tool name: 'Maven3', type: 'maven'
//                     env.PATH = "${mvnHome}/bin:${env.PATH}"
//                 }
//             }
//         }

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/vickneee/javafx_with_mariadb.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean package -DskipTests'
                    } else {
                        bat 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    if (isUnix()) {
                        sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    } else {
                        bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                    }
                }
            }
        }
    }

    post {
        always {
            junit(testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true)
            jacoco(execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java', inclusionPattern: '**/*.class', exclusionPattern: '')
        }
    }

}

