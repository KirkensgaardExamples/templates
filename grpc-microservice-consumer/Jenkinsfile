pipeline {
    agent any
    stages {
        stage('Clone Generator From Github'){
            steps{
                git branch: 'main',
                    url: 'https://github.com/JarlKDue/quarkus-gradle-template/'
            }
        }
        
        stage('Generate Project'){
            environment{
                gitUrl = "https://github.com/KirkensgaardExamples/${projectName}"
                dockerImage ="kirkensgaardexamples/generated-projects:${projectName}"
                providedEmail = "${providedEmail}"
                name = "${name}"
            }
            steps{
                sh 'chmod +x gradlew'
                sh './gradlew cleanArch generate -i -Dtarget=generated -Dcom.orctom.gradle.archetype.binding.name=${name} -Dcom.orctom.gradle.archetype.binding.dockerImage=${dockerImage} -Dcom.orctom.gradle.archetype.binding.providedEmail=${providedEmail} -Dcom.orctom.gradle.archetype.binding.gitUrl=${gitUrl} -Dgroup=${group} -Dname=${projectName} -Dversion=1.0-SNAPSHOT'
            }
        }
        stage('Create Github Repo'){
            steps{
                sh """curl -i -H \"Authorization: token ${GITHUB_TOKEN}\" -d '{ "name": "${projectName}", "private": false}' https://api.github.com/user/repos"""
                }
        }
        stage('Push Code to Github Repo'){
            steps{
                dir('generated'){
                    sh 'git init'
                    sh 'git remote add origin git@github.com/KirkensgaardExamples/${projectName}.git'
                    sh 'git add .'
                    sh 'git commit -m "First Commit"'
                    sh "git push https://KirkensgaardExamples:${GITHUB_PASSWORD}@github.com/KirkensgaardExamples/${projectName}.git"
                }
            }
        }
    }
}