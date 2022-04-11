pipeline{

	agent any

	environment {
		DOCKERHUB_CREDENTIALS=credentials('kathi-docker-hub')
	}

	tools {
            // Install the Maven version configured as "M3" and add it to the path.
            maven "M3"
    }

	stages {

		stage('Build') {
                    steps {
                        // Get some code from a GitHub repository
                        git 'https://github.com/jglick/simple-maven-project-with-tests.git'

                        // Run Maven on a Unix agent.
                        withMaven{
                            sh "mvn -Dmaven.test.failure.ignore=true clean package"
                        }

                        // To run Maven on a Windows agent, use
                        // bat "mvn -Dmaven.test.failure.ignore=true clean package"
                    }

                    post {
                        // If Maven was able to run the tests, even if some of the test
                        // failed, record the test results and archive the jar file.
                        success {
                            junit '**/target/surefire-reports/TEST-*.xml'
                            archiveArtifacts 'target/*.jar'
                        }
                    }
        }

        stage('Test') {
                    steps {
                        withMaven{
                            sh "mvn test"
                        }
                    }
                    post {
                        // If Maven was able to run the tests, even if some of the test
                        // failed, record the test results and archive the jar file.
                        success {
                            junit '**/target/surefire-reports/TEST-*.xml'
                            archiveArtifacts 'target/*.jar'
                        }
                    }
        }

// 		stage('Login') {
//
// 			steps {
// 				sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
// 			}
// 		}

// 		stage('Build Image') {
//
// 		    steps {
// 		        sh "docker build -t tui_git/spring:deploy ."
// 		    }
// 		}
//
// 		stage('Push') {
//
// 			steps {
// 				sh 'docker push tui_gui/spring:deploy'
// 			}
// 		}
	}

// 	post {
// 		always {
// 			sh 'docker logout'
// 		}
// 	}

}