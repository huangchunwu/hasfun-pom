package cn.hasfun.framework.netty.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ServletNettyHandler extends SimpleChannelInboundHandler<HttpRequest> {

    private Servlet servlet;

    private ServletContext servletContext;

    public ServletNettyHandler(Servlet servlet) {
        this.servlet = servlet;
        this.servletContext = servlet.getServletConfig().getServletContext();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {

        if (!request.getDecoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        MockHttpServletRequest servletRequest = createServletRequest(request);
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();


        try {
            this.servlet.service(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }


        HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
        for (Object name : servletResponse.getHeaderNames()) {
            for (Object value : servletResponse.getHeaders((String) name)) {
//                response.addHeader((String) name, value);
                response.headers().add((String) name, value);
            }
        }

        // Write the initial line and the header.
        ctx.write(response);

        InputStream contentStream = new ByteArrayInputStream(servletResponse.getContentAsByteArray());

        // Write the content and flush it.
        ChannelFuture writeFuture = ctx.writeAndFlush(new ChunkedStream(contentStream));
        writeFuture.addListener(ChannelFutureListener.CLOSE);
    }

    private MockHttpServletRequest createServletRequest(HttpRequest httpRequest) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(httpRequest.getUri()).build();

        MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
        servletRequest.setRequestURI(uriComponents.getPath());
        servletRequest.setPathInfo(uriComponents.getPath());
        servletRequest.setMethod(httpRequest.getMethod().name());

        if (uriComponents.getScheme() != null) {
            servletRequest.setScheme(uriComponents.getScheme());
        }
        if (uriComponents.getHost() != null) {
            servletRequest.setServerName(uriComponents.getHost());
        }
        if (uriComponents.getPort() != -1) {
            servletRequest.setServerPort(uriComponents.getPort());
        }

        for (String name : httpRequest.headers().names()) {
            for (String value : httpRequest.headers().getAll(name)) {
                servletRequest.addHeader(name, value);
            }
        }

        if(httpRequest instanceof HttpContent) {
            HttpContent content = (HttpContent) httpRequest;
            ByteBuf buf = content.content();
            servletRequest.setContent(buf.toString(io.netty.util.CharsetUtil.UTF_8).getBytes());
        }
        try {
            if (uriComponents.getQuery() != null) {
                String query = UriUtils.decode(uriComponents.getQuery(), "UTF-8");
                servletRequest.setQueryString(query);
            }

            for (Map.Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
                for (String value : entry.getValue()) {
                    servletRequest.addParameter(
                            UriUtils.decode(entry.getKey(), "UTF-8"),
                            UriUtils.decode(value, "UTF-8"));
                }
            }
        }
        catch (UnsupportedEncodingException ex) {
            // shouldn't happen
        }
        return servletRequest;
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
        response.headers().add(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);

    }

}
