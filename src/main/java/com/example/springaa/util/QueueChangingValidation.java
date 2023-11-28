package com.example.springaa.util;

import com.example.springaa.entity.Queue;
import com.example.springaa.entity.UserResponse;
import com.example.springaa.services.QueueService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class QueueChangingValidation {
    private final QueueService queueService;

    public QueueChangingValidation(QueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * Перевіряє:
     * - чи є в даного користувача права на зміну черги,
     * - чи існує така черга,
     * - чи існує такий користувач,
     * - чи залогінений користувач
     * @param session сесія
     * @param queueId id черги
     * @return чи всі умови вірні
     */
    public boolean validatePermissions(HttpSession session, int queueId){
        UserResponse user =(UserResponse) session.getAttribute("user");
        if (user == null){
            return false;
        }
        Optional<Queue> queueOpt = queueService.getQueueById(queueId);
        if (queueOpt.isEmpty()){
            return false;
        }
        if (user.getId() != queueOpt.get().getOwner().getId()){
            return false;
        }
        return true;
    }
}
