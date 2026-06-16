// ============================================================
// Jenkinsfile - Declarative Pipeline for CucumberUIFramework
// ============================================================
// Prerequisites on Jenkins server:
//   - Java 21 installed and JAVA_HOME configured
//   - Maven 3.9+ installed and added to PATH
//   - Git plugin installed
//   - HTML Publisher Plugin (for Extent Report)
//   - Chrome/Chromium installed (for headless mode)
// ============================================================

pipeline {

    agent any

    // ─── Pipeline Parameters ─────────────────────────────────────────────────
    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Choose the browser for test execution'
        )
        string(
            name: 'TAGS',
            defaultValue: '@Regression',
            description: 'Cucumber tags to run (e.g. @Smoke, @Login, @Positive)'
        )
        string(
            name: 'THREAD_COUNT',
            defaultValue: '2',
            description: 'Number of parallel threads'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run tests in headless mode (recommended for CI)'
        )
        string(
            name: 'BRANCH_NAME_PARAM',
            defaultValue: 'main',
            description: 'Git branch to build'
        )
    }

    // ─── Environment Variables ────────────────────────────────────────────────
    environment {
        JAVA_HOME = tool 'JDK21'           // Must match your Jenkins JDK tool name
        MAVEN_HOME = tool 'Maven3'          // Must match your Jenkins Maven tool name
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"

        // Test config passed as system properties
        BROWSER = "${params.BROWSER}"
        TAGS = "${params.TAGS}"
        HEADLESS = "${params.HEADLESS}"
        THREAD_COUNT = "${params.THREAD_COUNT}"

        // Report paths
        EXTENT_REPORT = 'reports/extent/ExtentReport.html'
        TESTNG_REPORT = 'test-output/index.html'
    }

    // ─── Build Triggers ───────────────────────────────────────────────────────
    triggers {
        // Poll SCM every hour for changes
        pollSCM('H * * * *')
        // Or use: cron('0 2 * * *') for nightly run at 2 AM
    }

    stages {

        // ── Stage 1: Checkout ─────────────────────────────────────────────────
        stage('Checkout') {
            steps {
                echo "📥 Checking out branch: ${params.BRANCH_NAME_PARAM}"
                git(
                    url: 'https://github.com/YOUR_USERNAME/CucumberUIFramework.git',
                    branch: "${params.BRANCH_NAME_PARAM}",
                    credentialsId: 'github-credentials'   // Jenkins credential ID
                )
                echo "✅ Checkout complete"
            }
        }

        // ── Stage 2: Verify Environment ───────────────────────────────────────
        stage('Environment Check') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
                sh 'echo "Browser: ${BROWSER} | Tags: ${TAGS} | Headless: ${HEADLESS}"'
            }
        }

        // ── Stage 3: Clean & Compile ──────────────────────────────────────────
        stage('Build') {
            steps {
                echo "🔨 Cleaning and compiling project..."
                sh 'mvn clean compile test-compile -q'
                echo "✅ Build successful"
            }
        }

        // ── Stage 4: Execute Tests ────────────────────────────────────────────
        stage('Execute Tests') {
            steps {
                echo "🧪 Running tests: Tags=${TAGS} | Browser=${BROWSER} | Headless=${HEADLESS}"
                sh """
                    mvn test \
                        -Dbrowser=${BROWSER} \
                        -Dtags="${TAGS}" \
                        -Dheadless=${HEADLESS} \
                        -DthreadCount=${THREAD_COUNT} \
                        -Dmaven.test.failure.ignore=true \
                        -q
                """
            }
            post {
                always {
                    echo "Test execution complete (pass or fail)"
                }
            }
        }

        // ── Stage 5: Publish Reports ──────────────────────────────────────────
        stage('Publish Reports') {
            steps {
                echo "📊 Publishing test reports..."

                // Publish Extent HTML Report
                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports/extent',
                    reportFiles: 'ExtentReport.html',
                    reportName: '📊 Extent Test Report',
                    reportTitles: 'Automation Test Results'
                ])

                // Publish TestNG Report
                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output',
                    reportFiles: 'index.html',
                    reportName: '📋 TestNG Report',
                    reportTitles: 'TestNG Results'
                ])

                // Publish Cucumber HTML Report
                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports/cucumber',
                    reportFiles: 'cucumber-report.html',
                    reportName: '🥒 Cucumber Report',
                    reportTitles: 'Cucumber BDD Results'
                ])

                echo "✅ Reports published"
            }
        }

        // ── Stage 6: Archive Artifacts ────────────────────────────────────────
        stage('Archive Artifacts') {
            steps {
                archiveArtifacts(
                    artifacts: 'reports/**/*,test-output/**/*',
                    allowEmptyArchive: true,
                    fingerprint: true
                )
            }
        }
    }

    // ─── Post Actions ─────────────────────────────────────────────────────────
    post {

        success {
            echo "✅ BUILD PASSED - All tests executed successfully"
            // Uncomment to send email notification:
            // emailext(
            //     subject: "✅ [PASSED] ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            //     body: "Tests passed. Report: ${env.BUILD_URL}Extent_Report/",
            //     to: 'team@company.com'
            // )
        }

        failure {
            echo "❌ BUILD FAILED - Some tests failed. Check reports for details."
            // Uncomment for email on failure:
            // emailext(
            //     subject: "❌ [FAILED] ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            //     body: "Tests failed. Report: ${env.BUILD_URL}Extent_Report/",
            //     to: 'team@company.com'
            // )
        }

        unstable {
            echo "⚠️ BUILD UNSTABLE - Some tests failed but pipeline continued"
        }

        always {
            echo "Pipeline finished. Build: ${env.BUILD_NUMBER}"
            // Clean workspace (optional)
            // cleanWs()
        }
    }
}
