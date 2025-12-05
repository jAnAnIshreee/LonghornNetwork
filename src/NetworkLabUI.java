// FULL FILE BELOW — DO NOT ADD ANYTHING ABOVE
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class NetworkLabUI extends JFrame {


    private JComboBox<String> testCaseSelector;
    private JButton runTestsButton;
    private JTextArea testOutputArea;


    private GraphPanel graphPanel;
    private GraphPanel roommateGraphPanel;
    private GraphPanel referralGraphPanel;


    private JComboBox<String> startStudentSelector;
    private JComboBox<String> targetCompanySelector;
    private JLabel referralPathLabel;


    private JTextArea friendChatArea; // new tab display


    private List<List<UniversityStudent>> testCases;


    public NetworkLabUI() {
        super("Longhorn Network Lab UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);


        // ============================================================
        // Pink & cherry global UI
        // ============================================================
        UIManager.put("TabbedPane.background", new Color(255, 200, 230));
        UIManager.put("TabbedPane.foreground", new Color(150, 20, 60));
        UIManager.put("TabbedPane.selected", new Color(255, 140, 210));


        testCases = Arrays.asList(
                Main.generateTestCase1(),
                Main.generateTestCase2(),
                Main.generateTestCase3()
        );


        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Test Runner", createTestRunnerPanel());
        tabs.addTab("Graph Viewer", createGraphViewerPanel());
        tabs.addTab("Roommate Pairs", createRoommatePanel());
        tabs.addTab("Referral Path", createReferralPanel());
        tabs.addTab("Friend & Chat Visualizer", createFriendChatPanel());
        add(tabs);
    }


    // ============================================================
    // TEST RUNNER TAB
    // ============================================================
    private JPanel createTestRunnerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();


        testCaseSelector = new JComboBox<>(new String[]{
                "Test Case 1", "Test Case 2", "Test Case 3", "All Test Cases"
        });


        runTestsButton = new JButton("Run Tests");
        runTestsButton.setForeground(new Color(150, 20, 60));
        runTestsButton.addActionListener(e -> onRunTests());


        top.add(new JLabel("Select Test Case:"));
        top.add(testCaseSelector);
        top.add(runTestsButton);


        panel.add(top, BorderLayout.NORTH);


        testOutputArea = new JTextArea();
        testOutputArea.setEditable(false);
        testOutputArea.setBackground(new Color(255, 220, 240));
        testOutputArea.setForeground(new Color(150, 20, 60));
        testOutputArea.setFont(new Font("Inter", Font.PLAIN, 14));
        panel.add(new JScrollPane(testOutputArea), BorderLayout.CENTER);


        return panel;
    }


    // ============================================================
    // GRAPH VIEWER TAB
    // ============================================================
    private JPanel createGraphViewerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel controls = new JPanel();


        JComboBox<String> graphCaseSelector =
                new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});


        JButton loadGraphButton = new JButton("Load Graph");
        loadGraphButton.setForeground(new Color(150, 20, 60));
        loadGraphButton.addActionListener(e -> {
            int idx = graphCaseSelector.getSelectedIndex();
            List<UniversityStudent> data = testCases.get(idx);
            StudentGraph graph = new StudentGraph(data);
            graphPanel.setGraph(graph, data);
        });


        controls.add(new JLabel("Select Data:"));
        controls.add(graphCaseSelector);
        controls.add(loadGraphButton);


        panel.add(controls, BorderLayout.NORTH);


        graphPanel = new GraphPanel();
        panel.add(graphPanel, BorderLayout.CENTER);


        return panel;
    }


    // ============================================================
    // ROOMMATE PAIRS TAB
    // ============================================================
    private JPanel createRoommatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel controls = new JPanel();


        JComboBox<String> rmCaseSelector =
                new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});


        JButton computeButton = new JButton("Compute Roommates");
        computeButton.setForeground(new Color(150, 20, 60));
        computeButton.addActionListener(e -> {
            int idx = rmCaseSelector.getSelectedIndex();
            List<UniversityStudent> data = testCases.get(idx);


            data.forEach(s -> s.setRoommate(null));
            GaleShapley.assignRoommates(data);


            List<Pair<UniversityStudent, UniversityStudent>> highlightPairs = new ArrayList<>();
            for (UniversityStudent s : data) {
                if (s.getRoommate() != null &&
                        s.getName().compareTo(s.getRoommate().getName()) < 0) {
                    highlightPairs.add(new Pair<>(s, s.getRoommate()));
                }
            }


            StudentGraph graph = new StudentGraph(data);
            roommateGraphPanel.setGraph(graph, data);
            roommateGraphPanel.setHighlightedPairs(highlightPairs);
        });


        controls.add(new JLabel("Select Data:"));
        controls.add(rmCaseSelector);
        controls.add(computeButton);


        panel.add(controls, BorderLayout.NORTH);


        roommateGraphPanel = new GraphPanel();
        panel.add(roommateGraphPanel, BorderLayout.CENTER);


        return panel;
    }


    // ============================================================
    // REFERRAL PATH TAB
    // ============================================================
    private JPanel createReferralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel controls = new JPanel();


        JComboBox<String> refCaseSelector =
                new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});


        startStudentSelector = new JComboBox<>();
        targetCompanySelector = new JComboBox<>();
        referralPathLabel = new JLabel(" ");


        JButton findButton = new JButton("Find Path");
        findButton.setForeground(new Color(150, 20, 60));
        findButton.addActionListener(e -> {
            int idx = refCaseSelector.getSelectedIndex();
            List<UniversityStudent> data = testCases.get(idx);


            String selectedName = (String) startStudentSelector.getSelectedItem();
            UniversityStudent start = data.stream()
                    .filter(s -> s.getName().equals(selectedName))
                    .findFirst().orElse(null);


            String target = (String) targetCompanySelector.getSelectedItem();


            if (start != null && target != null) {
                StudentGraph graph = new StudentGraph(data);
                ReferralPathFinder finder = new ReferralPathFinder(graph);
                List<UniversityStudent> path = finder.findReferralPath(start, target);


                StringBuilder sb = new StringBuilder();
                for (UniversityStudent s : path) {
                    sb.append(s.getName()).append(" -> ");
                }
                if (!path.isEmpty()) sb.setLength(sb.length() - 4);


                referralPathLabel.setText(sb.length() == 0 ? "No path found" : sb.toString());


                List<Pair<UniversityStudent, UniversityStudent>> highlightPairs = new ArrayList<>();
                for (int i = 0; i < path.size() - 1; i++) {
                    highlightPairs.add(new Pair<>(path.get(i), path.get(i + 1)));
                }


                referralGraphPanel.setGraph(graph, data);
                referralGraphPanel.animatePath(highlightPairs, new Color(230, 126, 34), 350);
            }
        });


        refCaseSelector.addActionListener(e -> {
            int idx = refCaseSelector.getSelectedIndex();
            List<UniversityStudent> data = testCases.get(idx);


            startStudentSelector.removeAllItems();
            targetCompanySelector.removeAllItems();
            Set<String> companies = new TreeSet<>();


            for (UniversityStudent s : data) {
                startStudentSelector.addItem(s.getName());


                for (String comp : s.getCompanies()) {
                    if (comp != null && !comp.equalsIgnoreCase("None")) {
                        companies.add(comp);
                    }
                }
            }


            for (String c : companies) {
                targetCompanySelector.addItem(c);
            }
        });


        refCaseSelector.setSelectedIndex(0);


        controls.add(new JLabel("Data:"));
        controls.add(refCaseSelector);
        controls.add(new JLabel("Start:"));
        controls.add(startStudentSelector);
        controls.add(new JLabel("Target Company:"));
        controls.add(targetCompanySelector);
        controls.add(findButton);


        panel.add(controls, BorderLayout.NORTH);


        referralGraphPanel = new GraphPanel();
        panel.add(referralGraphPanel, BorderLayout.CENTER);


        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(referralPathLabel, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);


        return panel;
    }


    // ============================================================
// FRIEND & CHAT TAB WITH BUBBLES AND FRIEND LINES
// ============================================================
    private JPanel createFriendChatPanel() {
        JPanel panel = new JPanel(new BorderLayout());


        JPanel top = new JPanel();
        JComboBox<String> fcCaseSelector = new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});
        JButton loadButton = new JButton("Load Friend & Chat Data");
        loadButton.setForeground(new Color(150, 20, 60));
        top.add(new JLabel("Select Data:"));
        top.add(fcCaseSelector);
        top.add(loadButton);
        panel.add(top, BorderLayout.NORTH);


        FriendChatPanel friendChatPanel = new FriendChatPanel();
        JScrollPane scroll = new JScrollPane(friendChatPanel);
        panel.add(scroll, BorderLayout.CENTER);


        loadButton.addActionListener(e -> {
            int idx = fcCaseSelector.getSelectedIndex();
            friendChatPanel.setStudents(testCases.get(idx));
        });


        return panel;
    }


    // ============================================================
// FRIEND CHAT PANEL - CLEAN VERSION
// ============================================================
    private static class FriendChatPanel extends JPanel {
        private List<UniversityStudent> students = new ArrayList<>();


        public FriendChatPanel() {
            setBackground(new Color(255, 220, 240));
            setLayout(null);
        }


        public void setStudents(List<UniversityStudent> data) {
            this.students = data;
            revalidate();
            repaint();
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (students == null) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int panelWidth = getWidth();

            // Draw column headers
            g2.setColor(new Color(150, 20, 60));
            g2.setFont(new Font("Inter", Font.BOLD, 16));
            FontMetrics fmLabel = g2.getFontMetrics();

            // Chats header (centered above left bubble column)
            int chatColumnX = 40;
            int chatColumnWidth = 200; // approximate width of chat column
            int chatX = chatColumnX + chatColumnWidth / 2 - fmLabel.stringWidth("Chats") / 2;
            g2.drawString("Chats", chatX, 30);

            // Friends header (centered above right bubble column)
            int friendsColumnX = panelWidth - 250;
            int friendsColumnWidth = 200; // approximate width of friends column
            int friendsX = friendsColumnX + friendsColumnWidth / 2 - fmLabel.stringWidth("Friends") / 2 - 130;
            g2.drawString("Friends", friendsX, 30);

            int y = 50; // start below headers
            Map<String, Point> namePos = new HashMap<>();

            for (UniversityStudent s : students) {
                // Draw the student name
                g2.setColor(new Color(150, 20, 60));
                g2.setFont(new Font("Inter", Font.BOLD, 16));
                g2.drawString(s.getName(), 20, y);
                namePos.put(s.getName(), new Point(20, y - 10));

                int bubbleStartY = y + 10;

                // Friend bubbles (just names of friends, no messages)
                int frY = bubbleStartY;
                Set<String> uniqueFriends = new LinkedHashSet<>(s.getFriendRequests());
                if (uniqueFriends.isEmpty()) uniqueFriends.add("None");
                for (String fr : uniqueFriends) {
                    drawBubble(g2, panelWidth - 250, frY, fr, new Color(255, 180, 210), false);
                    frY += 35;
                }

                // Chat messages for THIS student ONLY
                int chatY = bubbleStartY;
                Set<String> uniqueChats = new LinkedHashSet<>(s.getChatHistory());
                if (uniqueChats.isEmpty()) uniqueChats.add("No messages");
                for (String msg : uniqueChats) {
                    drawBubble(g2, 40, chatY, msg, new Color(230, 140, 210), true);
                    chatY += 35;
                }

                y = Math.max(frY, chatY) + 20;
            }

            // Draw friend lines (optional)
            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(150, 20, 60, 120));
            double shrinkFactor = 0.7; // line length factor
            int verticalOffset = 5; // start a little below the student name
            for (UniversityStudent s : students) {
                Point p1 = namePos.get(s.getName());
                int startX = p1.x;
                int startY = p1.y + verticalOffset; // shift down a bit
                for (String fr : s.getFriendRequests()) {
                    Point p2 = namePos.get(fr);
                    if (p2 != null) {
                        int dx = p2.x - startX;
                        int dy = p2.y - startY;
                        int endX = startX + (int)(dx * shrinkFactor);
                        int endY = startY + (int)(dy * shrinkFactor);
                        g2.drawLine(startX, startY, endX, endY);
                    }
                }
            }

            setPreferredSize(new Dimension(panelWidth, y + 50));
        }






        private void drawBubble(Graphics2D g2, int x, int y, String text, Color color, boolean left) {
            FontMetrics fm = g2.getFontMetrics();
            int width = fm.stringWidth(text) + 20;
            int height = fm.getHeight() + 10;
            if (!left) x -= width;


            g2.setColor(color);
            g2.fillRoundRect(x, y, width, height, 20, 20);
            g2.setColor(new Color(150, 20, 60));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width, height, 20, 20);
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 10, y + fm.getAscent());
        }
    }




    // ============================================================
    // TEST LOGIC
    // ============================================================
    private void onRunTests() {
        testOutputArea.setText("");
        String sel = (String) testCaseSelector.getSelectedItem();
        if (sel.equals("All Test Cases")) {
            for (int i = 1; i <= testCases.size(); i++) runTests(i);
        } else {
            int num = Integer.parseInt(sel.split(" ")[2]);
            runTests(num);
        }
    }


    private void runTests(int caseNum) {
        testOutputArea.append("=== Test Case " + caseNum + " ===\n");
        List<UniversityStudent> data = testCases.get(caseNum - 1);
        data.forEach(s -> testOutputArea.append(s + "\n"));
        testOutputArea.append("\n");


        int score = Main.gradeLab(data, caseNum);
        testOutputArea.append("Test Case " + caseNum + " Score: " + score + "\n\n");
    }


    // ============================================================
    // GRAPH PANEL WITH PINK GRADIENT
    // ============================================================
    private static class GraphPanel extends JPanel {
        private StudentGraph graph;
        private List<UniversityStudent> nodes;
        private List<Pair<UniversityStudent, UniversityStudent>> highlightedPairs = new ArrayList<>();


        private final Color[] PAIR_COLORS = {
                Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE,
                Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW
        };


        private javax.swing.Timer animationTimer = null;
        private List<Pair<UniversityStudent, UniversityStudent>> animQueue = new ArrayList<>();
        private int animIndex = 0;
        private Color animColor = Color.ORANGE;


        void setGraph(StudentGraph g, List<UniversityStudent> data) {
            stopAnimation();
            this.graph = g;
            this.nodes = data;
            this.highlightedPairs.clear();
            repaint();
        }


        void setHighlightedPairs(List<Pair<UniversityStudent, UniversityStudent>> pairs) {
            stopAnimation();
            this.highlightedPairs = new ArrayList<>(pairs);
            repaint();
        }


        void animatePath(List<Pair<UniversityStudent, UniversityStudent>> pairs, Color color, int delayMs) {
            stopAnimation();
            if (pairs == null || pairs.isEmpty()) return;


            this.animQueue = new ArrayList<>(pairs);
            this.animIndex = 0;
            this.animColor = color;


            highlightedPairs = new ArrayList<>();


            animationTimer = new javax.swing.Timer(delayMs, e -> {
                if (animIndex >= animQueue.size()) {
                    stopAnimation();
                    return;
                }
                highlightedPairs.add(animQueue.get(animIndex));
                animIndex++;
                repaint();
            });


            animationTimer.setInitialDelay(100);
            animationTimer.start();
        }


        private void stopAnimation() {
            if (animationTimer != null) {
                animationTimer.stop();
                animationTimer = null;
            }
            animQueue.clear();
            animIndex = 0;
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(255, 200, 230),
                    0, getHeight(), new Color(255, 140, 210)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());


            if (graph == null || nodes == null) return;


            int width = getWidth(), height = getHeight();
            int r = Math.min(width, height) / 3;
            int cx = width / 2, cy = height / 2;


            Map<UniversityStudent, Point> coords = new HashMap<>();
            int n = nodes.size();
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n;
                int x = cx + (int) (r * Math.cos(angle));
                int y = cy + (int) (r * Math.sin(angle));
                coords.put(nodes.get(i), new Point(x, y));
            }


            Color edgeColor = new Color(150, 20, 60);
            Color weightTextColor = new Color(140, 30, 60);
            Color nodeInner = new Color(255, 185, 215);
            Color nodeOuter = new Color(225, 140, 175);
            Color labelColor = new Color(90, 30, 60);


            int nodeRadius = 28;
            int diameter = nodeRadius * 2;


            for (UniversityStudent s : nodes) {
                for (StudentGraph.Edge e : graph.getNeighbors(s)) {
                    UniversityStudent t = e.neighbor;


                    if (nodes.indexOf(t) <= nodes.indexOf(s)) continue;


                    Point p1 = coords.get(s);
                    Point p2 = coords.get(t);


                    g2.setColor(edgeColor);
                    g2.setStroke(new BasicStroke(3.2f));
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);


                    int mx = (p1.x + p2.x) / 2;
                    int my = (p1.y + p2.y) / 2;


                    double dx = p2.x - p1.x;
                    double dy = p2.y - p1.y;
                    double len = Math.sqrt(dx * dx + dy * dy);


                    double offsetX = -dy / len * 28;
                    double offsetY = dx / len * 28;


                    int labelX = (int) (mx + offsetX);
                    int labelY = (int) (my + offsetY);


                    g2.setColor(weightTextColor);
                    g2.setFont(new Font("Inter", Font.BOLD, 15));
                    g2.drawString(String.valueOf(e.weight), labelX, labelY);
                }
            }


            for (int i = 0; i < highlightedPairs.size(); i++) {
                Pair<UniversityStudent, UniversityStudent> pair = highlightedPairs.get(i);
                Color c = animQueue.isEmpty()
                        ? PAIR_COLORS[i % PAIR_COLORS.length]
                        : animColor;


                g2.setColor(c);
                g2.setStroke(new BasicStroke(5));


                Point p1 = coords.get(pair.a);
                Point p2 = coords.get(pair.b);
                if (p1 != null && p2 != null)
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }


            for (UniversityStudent s : nodes) {
                Point p = coords.get(s);


                g2.setColor(nodeOuter);
                g2.fillOval(p.x - nodeRadius, p.y - nodeRadius, diameter, diameter);


                g2.setColor(nodeInner);
                g2.fillOval(p.x - nodeRadius + 4, p.y - nodeRadius + 4, diameter - 8, diameter - 8);


                g2.setColor(new Color(120, 40, 80));
                g2.setStroke(new BasicStroke(2.6f));
                g2.drawOval(p.x - nodeRadius, p.y - nodeRadius, diameter, diameter);


                g2.setColor(labelColor);
                g2.setFont(new Font("Inter", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int tx = p.x - fm.stringWidth(s.getName()) / 2;
                int ty = p.y + fm.getHeight() / 4;
                g2.drawString(s.getName(), tx, ty);
            }
        }
    }


    private static class Pair<A, B> {
        public final A a;
        public final B b;
        public Pair(A a, B b) { this.a = a; this.b = b; }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetworkLabUI().setVisible(true));
    }
}

