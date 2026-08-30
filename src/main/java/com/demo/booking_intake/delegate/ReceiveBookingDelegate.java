package com.demo.booking_intake.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Service Task w procesie "Booking Intake" woła ten bean.
 * Na razie tylko wypisuje do logu, zeby zobaczyc, ze krok sie wykonal.
 * Nazwa beana (domyslnie: receiveBookingDelegate) jest uzyta w BPMN
 * jako ${receiveBookingDelegate}.
 */
@Component
public class ReceiveBookingDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ReceiveBookingDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        // execution = uchwyt silnika do tej konkretnej instancji procesu
        String processInstanceId = execution.getProcessInstanceId();

        log.info(">>> Service Task wykonany! Przetwarzam booking. Instancja procesu: {}",
                processInstanceId);
    }
}
