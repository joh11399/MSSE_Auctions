

package msse_auctions

import grails.plugin.remotecontrol.RemoteControl
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.http.NameValuePair
import org.apache.http.client.CookieStore
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair

// A simple utility class to assist in making HTTP REST Calls
// It reuses an http client and cookie store between calls to support preserving a logged in status
class HttpUtils {

    JsonSlurper jsonSlurper
    CookieStore cookieStore
    CloseableHttpClient httpClient

    RequestConfig globalConfig

    HttpUtils() {
        jsonSlurper = new JsonSlurper()
        cookieStore = new BasicCookieStore()
        globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
        httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
    }

    def doGet(String url) {
        if (!url.startsWith(RemoteControl.functionalTestBaseUrl + '/')) {
            url = RemoteControl.functionalTestBaseUrl + url
        }

        def request = new HttpGet(url)
        return performRequest(request)
    }

    // Use this method to submit an HTML form-style data (for example from the login screen)
    def doFormPost(String path, Map formData) {
        String url = RemoteControl.functionalTestBaseUrl + path

        def request = new HttpPost(url)

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        formData.each { k, v ->
            urlParameters.add(new BasicNameValuePair(k as String, v as String));
        }
        request.setEntity(new UrlEncodedFormEntity(urlParameters));

        return performRequest(request)
    }

    // Use this method to submit JSON data via a POST
    def doJsonPost(String path, Object data) {
        String url = RemoteControl.functionalTestBaseUrl + path

        def request = new HttpPost(url)
        def entity = new StringEntity(JsonOutput.toJson(data), 'UTF8')
        entity.setContentType('application/json')
        request.setEntity(entity)
        return performRequest(request)
    }

    // Use this method to submit JSON data via a PUT
    def doJsonPut(String path, Object data) {
        String url = RemoteControl.functionalTestBaseUrl + path

        def request = new HttpPut(url)
        def entity = new StringEntity(JsonOutput.toJson(data), 'UTF8')
        entity.setContentType('application/json')
        request.setEntity(entity)
        return performRequest(request)
    }


    // Use this method to submit JSON data via a DELETE
    def doJsonDelete(String path, Object data) {
        String url = RemoteControl.functionalTestBaseUrl + path

        def request = new HttpDelete(url)

        return performRequest(request)
    }

    def performRequest(HttpUriRequest request) {
        HttpClientContext context = HttpClientContext.create()
        context.cookieStore = cookieStore
        def response = httpClient.execute(request, context)
        String str = new String(response.entity?.content?.bytes, 'UTF-8');
        def contentType = response.entity?.contentType?.value
        if (contentType?.contains(';charset=')) {
            contentType = contentType.split(';')[0]
        }
        def data = str
        if (contentType?.contains('json')) {
            data = jsonSlurper.parseText(str)
        }
        return [
                status     : response.statusLine.statusCode,
                statusText : response.statusLine.reasonPhrase,
                contentType: contentType,
                data       : data,
                headers    : response.allHeaders
        ]
    }
}
