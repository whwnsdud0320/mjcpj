package view;

import javax.swing.*;
import java.awt.*;

public class StaffManageWin extends JPanel {
    public StaffManageWin() {
        // 1. 패키지 기본 레이아웃을 BorderLayout으로 설정
        setLayout(new BorderLayout());

        // 2. 왼쪽과 오른쪽을 나눌 JSplitPane 생성
        String[] staffCols = {"사번", "이름", "연락처", "권한", "시급", "상태"};
        Object[][] staffData = {{1001, "조준영", "010-1111-2222", "ADMIN", "12,000원", "재직"}};
        JTable staffTable = new JTable(staffData, staffCols);
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setBorder(BorderFactory.createTitledBorder("직원 목록 및 시급 설정"));
        pnlLeft.add(new JScrollPane(staffTable), BorderLayout.CENTER);

        String[] attendCols = {"근무일자", "출근시각", "퇴근시각", "근무시간(분)", "일일급여"};
        Object[][] attendData = {{"2026-11-01", "09:00:00", "18:00:00", "540분", "108,000원"}};
        JTable attendTable = new JTable(attendData, attendCols);
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(BorderFactory.createTitledBorder("선택 직원 근태 기록 및 급여 자동 계산"));
        pnlRight.add(new JScrollPane(attendTable), BorderLayout.CENTER);

        // JSplitPane에 좌우 패널 배치
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLeft, pnlRight);
        splitPane.setDividerLocation(600);

        // 3. 패키지 중앙에 분할 창 추가
        add(splitPane, BorderLayout.CENTER);
    }
}