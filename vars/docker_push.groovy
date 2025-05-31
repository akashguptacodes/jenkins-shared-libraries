def call(Map config = [:]) {
    def imageName = config.imageName ?: error("Image name is required")
    def imageTag = config.imageTag ?: 'latest'
    def credentials = config.credentials ?: 'dockerHubCred'
    
    echo "Pushing Docker image: ${imageName}:${imageTag}"
    
    withCredentials([usernamePassword(credentialsId: ${credentials}, passwordVariable: "dockerHubPass", usernameVariable: "dockerHubUser")]){
        sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
        sh "docker image tag ${imageName}:${imageTag} ${env.dockerHubUser}/${imageName}-by-jenkins-onhub:${imageTag}"
        sh "docker push ${env.dockerHubUser}/${imageName}-by-jenkins-onhub:${imageTag}"                    
    }
}
