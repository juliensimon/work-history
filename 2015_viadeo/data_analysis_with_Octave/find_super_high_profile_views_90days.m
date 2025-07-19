data = csvread("event_breakdown_by_member_90days.csv");
memberids = data(:,1);
profiles=data(:,13);
idx = find(profiles > 7500);
ids = memberids(idx);
save high_profiles_90days.txt ids


