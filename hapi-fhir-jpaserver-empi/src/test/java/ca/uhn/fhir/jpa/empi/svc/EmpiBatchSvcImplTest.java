package ca.uhn.fhir.jpa.empi.svc;

import ca.uhn.fhir.interceptor.api.IInterceptorService;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.jpa.empi.BaseEmpiR4Test;
import ca.uhn.test.concurrency.PointcutLatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EmpiBatchSvcImplTest extends BaseEmpiR4Test {

	@Autowired
	EmpiBatchSvcImpl myEmpiBatchSvc;

	@Autowired
	IInterceptorService myInterceptorService;
	PointcutLatch afterEmpiLatch = new PointcutLatch(Pointcut.EMPI_AFTER_PERSISTED_RESOURCE_CHECKED);

	@BeforeEach
	public void before() {
		myInterceptorService.registerAnonymousInterceptor(Pointcut.EMPI_AFTER_PERSISTED_RESOURCE_CHECKED, afterEmpiLatch);
	}
	@AfterEach
	public void after() {
		myInterceptorService.unregisterInterceptor(afterEmpiLatch);
		afterEmpiLatch.clear();
		super.after();
	}

	@Test
	public void testEmpiBatchRunWorksOverMultipleTargetTypes() throws InterruptedException {

		for (int i =0; i < 10; i++) {
			createPatient(buildJanePatient());
		}

		for(int i = 0; i< 10; i++) {
			createPractitioner(buildPractitionerWithNameAndId("test", "id"));
		}

		assertLinkCount(0);
		afterEmpiLatch.setExpectedCount(20);

		//SUT
		myEmpiBatchSvc.runEmpiOnAllTargets();

		afterEmpiLatch.awaitExpected();
		assertLinkCount(20);
	}

	@Test
	public void testEmpiBatchOnPatientType() throws Exception {

		for (int i =0; i < 10; i++) {
			createPractitioner(buildPractitionerWithNameAndId("test", "id"));
		}

		assertLinkCount(0);
		afterEmpiLatch.setExpectedCount(10);

		//SUT
		myEmpiBatchSvc.runEmpiOnAllTargets();

		afterEmpiLatch.awaitExpected();
		assertLinkCount(10);
	}

	@Test
	public void testEmpiBatchOnPractitionerType() throws Exception {

		for (int i =0; i < 10; i++) {
			createPractitioner(buildPractitionerWithNameAndId("test", "id"));
		}

		assertLinkCount(0);
		afterEmpiLatch.setExpectedCount(10);

		//SUT
		myEmpiBatchSvc.runEmpiOnAllTargets();

		afterEmpiLatch.awaitExpected();
		assertLinkCount(10);
	}


}
