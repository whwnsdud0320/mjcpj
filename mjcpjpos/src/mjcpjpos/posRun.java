package mjcpjpos;

import mjcpjpos.owner.mainPosInterface;

public class posRun {
	
	public void posRun() {
		mainPosInterface mpi = new mainPosInterface();
		
		// 로그인후 보여질 메인 포스기 화면 
		mpi.mainPos();
	}

}
