package com.example.springaa.util;

import com.example.springaa.entity.Queue;
import com.example.springaa.web.dto.UserResponse;
import com.example.springaa.services.QueueService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@AllArgsConstructor
public class QueuesAccessValidation {
    private final QueueService queueService;


    /**
     * Перевіряє:
     * - чи існує така черга,
     * - чи залогінений юзер,
     * - чи є він власником
     * @param session сесія юзера
     * @param queueId id черги
     * @return empty, якщо все вірно, статус код в іншому випадку
     */
    public Optional<HttpStatus> isUserOwnerOfQueue(HttpSession session, int queueId){
        Optional<HttpStatus> result = isUserAuthorizedAndQueueExist(session, queueId);
        if (result.isPresent()){
            return result;
        }
        //Перевірка, чи є користувач овнером черги
        if (queueService.getQueueById(queueId).getOwner().getId() != ((UserResponse)session.getAttribute("user")).getId()) {
            return Optional.of(HttpStatus.FORBIDDEN); //403
        }
        return Optional.empty();

    }

    /**
     * Перевіряє:
     * - чи існує така черга,
     * - чи залогінений юзер
     * @param session сесія юзера
     * @param queueId id черги
     * @return empty, якщо все вірно, статус код в іншому випадку
     */
    public Optional<HttpStatus> isUserAuthorizedAndQueueExist(HttpSession session, int queueId){
        return isUserAuthorized(session)
                .or(() -> isQueueExist(queueId));
    }


    /**
     * Перевіряє:
     * - чи залогінений юзер
     * @param session сесія юзера
     * @return empty, якщо все вірно, статус код в іншому випадку
     */
    public Optional<HttpStatus> isUserAuthorized(HttpSession session){
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            return Optional.of(HttpStatus.UNAUTHORIZED); //401
        }
        return Optional.empty();
    }

    /**
     * Перевіряє:
     * - чи існує така черга,
     * @param queueId id черги
     * @return empty, якщо все вірно, статус код в іншому випадку
     */
    public Optional<HttpStatus> isQueueExist(int queueId){
        Queue queueOpt = queueService.getQueueById(queueId);
        return Optional.empty();
    }


}
