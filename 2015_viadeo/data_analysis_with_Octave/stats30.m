data 	= csvread("events_by_member_30days.csv");
premium = csvread("premium_members.csv");

[x,data_premium_idx,premium_idx]  = intersect(data(:,1),premium);
[x,data_non_premium_idx]          = setdiff(data(:,1), premium);

data_premium 	 = data(data_premium_idx,:);
data_non_premium = data(data_non_premium_idx, :);

vect_premium     = data_premium(:,2)';
vect_non_premium = data_non_premium(:,2)';

vect_premium2     = vect_premium(find(vect_premium<=300));
vect_non_premium2 = vect_non_premium(find(vect_non_premium<=300));

fprintf("\n");
fprintf("Premium members : %d\n", 			length(vect_premium))
fprintf("Average: %d, median: %d, stddev: %d\n",	mean(vect_premium), median(vect_premium), std(vect_premium));
fprintf("Members with less than 3 events : %d\n", 	length(vect_premium(find(vect_premium<=3))))
fprintf("Members with 3-30 events : %d\n", 		length(vect_premium(find(vect_premium(find(vect_premium>3))<=30))))
fprintf("Members with 30-300 events : %d\n", 		length(vect_premium(find(vect_premium(find(vect_premium>30))<=300))))
fprintf("Members with 300-3000 events : %d\n", 		length(vect_premium(find(vect_premium(find(vect_premium>300))<=3000))))
fprintf("Members with 3000-30000 events or less: %d\n", length(vect_premium(find(vect_premium(find(vect_premium>3000))<=30000))))
fprintf("Members with more than 30000 events : %d\n", 	length(vect_premium(find(vect_premium>30000))))
fprintf("\n");
fprintf("Non-premium members : %d\n", 			length(vect_non_premium))
fprintf("Average: %d, median: %d, stddev: %d\n",	mean(vect_non_premium), median(vect_non_premium), std(vect_non_premium));
fprintf("Members with less than 3 events : %d\n", 	length(vect_non_premium(find(vect_non_premium<=3))))
fprintf("Members with 3-30 events : %d\n", 		length(vect_non_premium(find(vect_non_premium(find(vect_non_premium>3))<=30))))
fprintf("Members with 30-300 events : %d\n", 		length(vect_non_premium(find(vect_non_premium(find(vect_non_premium>30))<=300))))
fprintf("Members with 300-3000 events : %d\n", 		length(vect_non_premium(find(vect_non_premium(find(vect_non_premium>300))<=3000))))
fprintf("Members with 3000-30000 events or less: %d\n", length(vect_non_premium(find(vect_non_premium(find(vect_non_premium>3000))<=30000))))
fprintf("Members with more than 30000 events : %d\n", 	length(vect_non_premium(find(vect_non_premium>30000))))
fprintf("\n");
