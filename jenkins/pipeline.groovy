def testSpringBoot(String workDir) {
    echo 'Tests Spring Boot...'
    dir(workDir) {
        sh './mvnw test -B'
    }
    junit "${workDir}/target/surefire-reports/*.xml"
}

def testAngular(String workDir) {
    echo 'Tests Angular...'
    dir(workDir) {
        sh 'npm ci'
        sh 'ng test --watch=false'
    }
}

def buildAndPush(String imageName, String imageTag, String workDir) {
    echo "Build et push de l'image ${imageName}:${imageTag}..."
    dir(workDir) {
        withCredentials([usernamePassword(
            credentialsId: 'dockerhub-credentials',
            passwordVariable: 'PASS',
            usernameVariable: 'USER'
        )]) {
            sh "docker build -t ${imageName}:${imageTag} ."
            sh 'echo $PASS | docker login -u $USER --password-stdin'
            sh "docker push ${imageName}:${imageTag}"
            sh "docker logout"
        }
    }
}

def deployToKubernetes(String imageName, String imageTag, String deploymentName, String namespace, String kubeconfig, String workDir) {
    echo "Déploiement ${deploymentName} sur Kubernetes namespace: ${namespace}..."
    sh "export KUBECONFIG=${kubeconfig}"
    sh "kubectl create namespace ${namespace} --dry-run=client -o yaml | kubectl apply -f -"
    sh "IMAGE_NAME=${imageName} IMAGE_TAG=${imageTag} envsubst '\${IMAGE_NAME},\${IMAGE_TAG}' < ${workDir}/k8s/deployment.yaml | kubectl apply -f -"
    sh "kubectl rollout status deployment/${deploymentName} -n ${namespace} --timeout=5m"
}

def cleanup(String springbootImage, String angularImage, String imageTag) {
    echo 'Nettoyage des images Docker locales...'
    sh "docker rmi ${springbootImage}:${imageTag} 2>/dev/null || true"
    sh "docker rmi ${angularImage}:${imageTag} 2>/dev/null || true"
    sh "docker image prune -f 2>/dev/null || true"
}

return this