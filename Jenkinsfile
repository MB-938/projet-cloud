def pipelineScript

pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'mb938'
        SPRINGBOOT_DIR = 'apps/hello_world'
        ANGULAR_DIR    = 'apps/hello-angular'
        K8S_NAMESPACE  = 'apps'
        KUBECONFIG     = '/var/jenkins_home/.kube/config-jenkins'
        IMAGE_TAG      = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Init') {
            steps {
                script {
                    pipelineScript = load 'jenkins/pipeline.groovy'
                }
            }
        }

        stage('Spring Boot — Tests') {
            steps {
                script {
                    pipelineScript.testSpringBoot(SPRINGBOOT_DIR)
                }
            }
        }

        stage('Spring Boot — Build & Push') {
            steps {
                script {
                    pipelineScript.buildAndPush("${DOCKERHUB_USER}/hello-springboot", IMAGE_TAG, SPRINGBOOT_DIR)
                }
            }
        }

        stage('Spring Boot — Deploy') {
            steps {
                script {
                    pipelineScript.deployToKubernetes("${DOCKERHUB_USER}/hello-springboot", IMAGE_TAG, 'springboot-hello', K8S_NAMESPACE, KUBECONFIG, SPRINGBOOT_DIR)
                }
            }
        }

        stage('Angular — Tests') {
            steps {
                script {
                    pipelineScript.testAngular(ANGULAR_DIR)
                }
            }
        }

        stage('Angular — Build & Push') {
            steps {
                script {
                    pipelineScript.buildAndPush("${DOCKERHUB_USER}/hello-angular", IMAGE_TAG, ANGULAR_DIR)
                }
            }
        }

        stage('Angular — Deploy') {
            steps {
                script {
                    pipelineScript.deployToKubernetes("${DOCKERHUB_USER}/hello-angular", IMAGE_TAG, 'angular-hello', K8S_NAMESPACE, KUBECONFIG, ANGULAR_DIR)
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    pipelineScript.cleanup("${DOCKERHUB_USER}/hello-springboot", "${DOCKERHUB_USER}/hello-angular", IMAGE_TAG)
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline réussi — Build #${BUILD_NUMBER}"
        }
        failure {
            echo "Pipeline échoué — Build #${BUILD_NUMBER}"
        }
    }
}