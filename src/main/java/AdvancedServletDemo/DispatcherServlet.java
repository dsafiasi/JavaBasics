package AdvancedServletDemo;

import ServletDemo.HelloServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HelloServlet {
    private Map<String, GetDispatcher> getMappings = new HashMap<>();
//    private Map<String, PostDispatcher> postMappings = new HashMap<>();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {



    }
}

class GetDispatcher {
    Object instance;// Controller实例
    Method method;// Controller方法
    String[] parameterNames;// 方法参数
    Class<?>[] parameterClasses; // 方法参数类型

    /**
     * 通过构造某个方法需要的所有参数列表，使用反射调用该方法后返回结果。
     * @param request
     * @param response
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else if (parameterClass == int.class) {
                arguments[i] = Integer.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == long.class) {
                arguments[i] = Long.valueOf(getOrDefault(request, parameterName, "0"));
            } else if (parameterClass == boolean.class) {
                arguments[i] = Boolean.valueOf(getOrDefault(request, parameterName, "false"));
            } else if (parameterClass == String.class) {
                arguments[i] = getOrDefault(request, parameterName, "");
            } else {
                throw new RuntimeException("Missing handler for type: " + parameterClass);
            }
        }
        return (ModelAndView) this.method.invoke(this.instance, arguments);
    }
    private String getOrDefault(HttpServletRequest request, String name, String defaultValue) {
        String s = request.getParameter(name);
        return s == null ? defaultValue : s;
    }


}
//class PostDispatcher {
//    Object instance; // Controller实例
//    Method method; // Controller方法
//    Class<?>[] parameterClasses; // 方法参数类型
//    ObjectMapper objectMapper; // JSON映射
//
//    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, InvocationTargetException, IllegalAccessException {
//        Object[] arguments = new Object[parameterClasses.length];
//        for (int i = 0; i < parameterClasses.length; i++) {
//            Class<?> parameterClass = parameterClasses[i];
//            if (parameterClass == HttpServletRequest.class) {
//                arguments[i] = request;
//            } else if (parameterClass == HttpServletResponse.class) {
//                arguments[i] = response;
//            } else if (parameterClass == HttpSession.class) {
//                arguments[i] = request.getSession();
//            } else {
//                // 读取JSON并解析为JavaBean:
//                BufferedReader reader = request.getReader();
//                arguments[i] = this.objectMapper.readValue(reader, parameterClass);
//            }
//        }
//        return (ModelAndView) this.method.invoke(instance, arguments);
//    }

//}
