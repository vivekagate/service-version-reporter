import http.client
import json
import subprocess

class ServiceVersionReporter:

    def getGitVersion():
        return subprocess.check_output(['git', 'rev-parse', 'HEAD']).decode('ascii').strip()

    def getGitTag():
        return 'v1.4.3'


    def report(url, org, servicename):
        print("Reporting Service Version to: " + url)
        conn = http.client.HTTPConnection(url)
        headers = {'Content-type': 'application/json'}

        gitversion = ServiceVersionReporter.getGitVersion()
        tag = ServiceVersionReporter.getGitTag()
        foo = {'name': servicename, 'gitversion': gitversion, 'tag': tag}
        json_data = json.dumps(foo)

        conn.request('PUT', '/api/'+org+'/service/api', json_data, headers)

        response = conn.getresponse()
        print(response.read().decode())

ServiceVersionReporter.report('localhost:3000', 'PYTHON_EXAMPLE', 'PYTHON_EXAMPLE_SERVICE')
