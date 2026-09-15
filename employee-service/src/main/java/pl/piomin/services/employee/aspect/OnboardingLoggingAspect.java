package pl.piomin.services.employee.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.piomin.services.employee.entity.Employee;

@Aspect
@Component
public class OnboardingLoggingAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(OnboardingLoggingAspect.class);

    @Around("execution(* pl.piomin.services.employee.service.EmployeeServiceImpl.onboardEmployee(..))")
    public Object logOnboarding(ProceedingJoinPoint joinPoint)
            throws Throwable {

        long startTime = System.currentTimeMillis();

        logger.info(
                "Onboarding started | method={} | args={}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs()
        );

        try {

            Object result = joinPoint.proceed();

            long executionTime =
                    System.currentTimeMillis() - startTime;

            Employee employee = (Employee) result;

            String outcome =
                    employee.getOnboardingStatus().toString();

            logger.info(
                    "Onboarding completed | method={} | outcome={} | time={}ms",
                    joinPoint.getSignature().getName(),
                    outcome,
                    executionTime
            );

            return result;

        } catch (Throwable exception) {

            long executionTime =
                    System.currentTimeMillis() - startTime;

            logger.error(
                    "Onboarding failed | method={} | time={}ms",
                    joinPoint.getSignature().getName(),
                    executionTime,
                    exception
            );

            throw exception;
        }
    }
}
