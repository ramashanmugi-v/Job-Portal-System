import java.util.*;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    private static final Scanner sc=new Scanner(System.in);
    private static final AuthService auth=new AuthService();
    private static final JobService jobs=new JobService();
    private static final ApplicationService apps=new ApplicationService();

    public static void main(String[] args) {
        while(true){
            System.out.println("\n===== JOB PORTAL SYSTEM =====\n1. Register\n2. Login\n3. Exit");
            String ch=read("Choose: ");
            try {
                switch(ch){
                    case "1": register(); break;
                    case "2": login(); break;
                    case "3": System.out.println("Thank you!"); return;
                    default: System.out.println("Invalid choice.");
                }
            } catch(Exception e){ System.out.println("Error: "+e.getMessage()); }
        }
    }
    private static void register() throws SQLException {
        String name=read("Name: "), email=read("Email: "), password=read("Password: ");
        System.out.println("1. Candidate\n2. Employer");
        String r=read("Choose role: ");
        String role=r.equals("1")?"Candidate":r.equals("2")?"Employer":"";
        if(role.isEmpty()){System.out.println("Invalid role.");return;}
        System.out.println(auth.register(name,email,password,role)?"Registration successful.":"Registration failed.");
    }
    private static void login() throws SQLException {
        String email=read("Email: "), password=read("Password: ");
        User u=auth.login(email,password);
        if(u==null){System.out.println("Invalid email or password.");return;}
        System.out.println("Welcome "+u.getName()+" ("+u.getRole()+")");
        if(u.getRole().equals("Employer")) employerMenu(u);
        else candidateMenu(u);
        System.out.println("Logged out.");
    }
    private static void employerMenu(User u) throws SQLException {
        while(true){
            System.out.println("\n--- EMPLOYER MENU ---\n1. Post job\n2. View my jobs\n3. Edit job\n4. Delete job\n5. View applications\n6. Update application status\n7. Logout");
            switch(read("Choose: ")){
                case "1": addJob(u); break;
                case "2": printJobs(jobs.employerJobs(u.getUserId())); break;
                case "3": editJob(u); break;
                case "4":
                    printJobs(jobs.employerJobs(u.getUserId()));
                    int del=number("Job ID to delete: ");
                    System.out.println(jobs.deleteJob(del,u.getUserId())?"Job deleted.":"Job not found/permission denied."); break;
                case "5": apps.viewEmployerApplications(u.getUserId()); break;
                case "6":
                    apps.viewEmployerApplications(u.getUserId());
                    int aid=number("Application ID: ");
                    String status=read("Status (Applied/Shortlisted/Rejected): ");
                    System.out.println(apps.updateStatus(aid,u.getUserId(),status)?"Status updated.":"Application not found/permission denied."); break;
                case "7": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    private static void candidateMenu(User u) throws SQLException {
        while(true){
            System.out.println("\n--- CANDIDATE MENU ---\n1. View/search/filter jobs\n2. Apply for job\n3. View applied jobs\n4. Logout");
            switch(read("Choose: ")){
                case "1":
                    String keyword=read("Keyword (blank for all): ");
                    String location=read("Location filter (blank for all): ");
                    String type=read("Job type filter (e.g. Full-time; blank for all): ");
                    printJobs(jobs.listJobs(keyword,location,type)); break;
                case "2":
                    printJobs(jobs.listJobs("","",""));
                    int jid=number("Job ID to apply: ");
                    try { System.out.println(apps.apply(jid,u.getUserId())?"Application submitted.":"Could not apply."); }
                    catch(java.sql.SQLIntegrityConstraintViolationException e){System.out.println("You have already applied to this job.");}
                    break;
                case "3": apps.viewCandidateApplications(u.getUserId()); break;
                case "4": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    private static void addJob(User u) throws SQLException {
        String title=read("Job title: "), company=read("Company name: "), location=read("Location: ");
        BigDecimal salary=decimal("Salary: ");
        String type=read("Job type (Full-time/Part-time/Internship/Contract): ");
        String desc=read("Description: "), skills=read("Required skills: ");
        System.out.println(jobs.addJob(u.getUserId(),title,company,location,salary,type,desc,skills)?"Job posted.":"Could not post job.");
    }
    private static void editJob(User u) throws SQLException {
        printJobs(jobs.employerJobs(u.getUserId()));
        int id=number("Job ID to edit: ");
        Job j=jobs.getJob(id);
        if(j==null || j.employerId!=u.getUserId()){System.out.println("Job not found/permission denied.");return;}
        System.out.println("Enter updated details:");
        String title=read("Job title: "), company=read("Company: "), location=read("Location: ");
        BigDecimal salary=decimal("Salary: ");
        String type=read("Job type: "), desc=read("Description: "), skills=read("Required skills: ");
        System.out.println(jobs.updateJob(id,u.getUserId(),title,company,location,salary,type,desc,skills)?"Job updated.":"Update failed.");
    }
    private static void printJobs(List<Job> list){
        if(list.isEmpty()) System.out.println("No jobs found.");
        else for(Job j:list) System.out.println(j);
    }
    private static String read(String prompt){System.out.print(prompt);return sc.nextLine().trim();}
    private static int number(String prompt){while(true){try{return Integer.parseInt(read(prompt));}catch(NumberFormatException e){System.out.println("Enter a valid number.");}}}
    private static BigDecimal decimal(String prompt){while(true){try{return new BigDecimal(read(prompt));}catch(NumberFormatException e){System.out.println("Enter a valid amount.");}}}
}
