grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

def defaultVMSettings = [
        maxMemory: 768,
        minMemory: 64,
        debug:     false,
        maxPerm:   256
]

grails.project.fork = [
        test:    [*: defaultVMSettings, daemon:      true],
        run:     [*: defaultVMSettings, forkReserve: false],
        war:     [*: defaultVMSettings, forkReserve: false],
        console: defaultVMSettings
]

grails.project.repos.default = 'repo.thehyve.nl-snapshots'
grails.project.repos."${grails.project.repos.default}".url = 'https://repo.thehyve.nl/content/repositories/snapshots/'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {
    log "warn"
    legacyResolve false

    inherits('global') {}
	pom true
    repositories {
        //  grailsPlugins()
        // grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        mavenRepo 'https://repo.transmartfoundation.org/content/repositories/public/'
        mavenRepo 'https://repo.thehyve.nl/content/repositories/public/'
    }

    dependencies {
        compile 'org.transmartproject:transmart-core-api:1.2.2-hackathon-SNAPSHOT'
        compile group: 'com.google.guava', name: 'guava', version: '14.0.1'

        runtime('org.postgresql:postgresql:9.3-1100-jdbc41') {
            transitive = false
            export     = false
        }
		
		test('junit:junit:4.11') {
			transitive = false /* don't bring hamcrest */
			export     = false
		}

		test('org.gmock:gmock:0.9.0-r435-hyve2') {
			transitive = false /* don't bring groovy-all */
			export     = false
		}

		/* for reasons I don't want to guess (we'll move away from ivy soon
		 * anyway), javassist is not being included in the test classpath
		 * when running test-app in Travis even though the hibernate plugin
		 * depends on it */
		test('org.javassist:javassist:3.16.1-GA') {
			export = false
		}
    }

    plugins {
        build ':tomcat:7.0.54'
        build ':release:3.0.1', ':rest-client-builder:2.0.1', {
            export = false
        }

        compile(':db-reverse-engineer:0.5') {
            export = false
        }

        runtime ':hibernate:3.6.10.16'
    }
}
