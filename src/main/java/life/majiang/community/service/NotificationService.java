package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(example);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

        // size * (page - 1)
        Integer offset = size * (page - 1);

        NotificationExample ex = new NotificationExample();
        ex.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper
                .selectByExampleWithRowbounds(ex, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return paginationDTO;
        }
        ArrayList<Long> userIds = notifications.stream().map(Notification::getNotifier)
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));


        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }
}
