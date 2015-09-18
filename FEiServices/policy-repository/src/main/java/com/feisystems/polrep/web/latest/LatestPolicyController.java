package com.feisystems.polrep.web.latest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feisystems.polrep.web.v1.PolicyControllerV1;

@RestController
@RequestMapping(value = "/rest/latest")
public class LatestPolicyController extends PolicyControllerV1 {

}
