import javax.servlet.http.HttpServletRequest;Add commentMore actions
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.filter.OncePerRequestFilter;


public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        res.addHeader("Access-Control-Allow-Origin", "*");

        if (req.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(req.getMethod())) {
            // CORS "pre-flight" request
            res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            res.addHeader("Access-Control-Allow-Headers", "Authorization");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type");
            res.addHeader("Access-Control-Max-Age", "1");
        }

        filterChain.doFilter(req, res);
    }
}
