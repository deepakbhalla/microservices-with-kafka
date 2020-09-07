/*
 * Copyright 2017 HomeAdvisor, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package com.homeadvisor.kafdrop.model;

import java.util.*;

public class ConsumerTopicVO
{
   private final String topic;
   private final String groupId;
   private final Map<Integer, ConsumerPartitionVO> offsets = new TreeMap<>();

   public ConsumerTopicVO(String topic, String groupId)
   {
      this.topic = topic;
      this.groupId = groupId;
   }

   public String getTopic()
   {
      return topic;
   }

   public void addOffset(ConsumerPartitionVO offset)
   {
      offsets.put(offset.getPartitionId(), offset);
   }

   public long getLag()
   {
      return offsets.values().stream()
         .map(ConsumerPartitionVO::getLag)
         .filter(lag -> lag >= 0)
         .reduce(0L, Long::sum);
   }

   public long getMaxLag()
   {
      return offsets.values().stream()
         .map(ConsumerPartitionVO::getLag)
         .filter(lag -> lag >= 0)
         .reduce(0L, Long::max);
   }

   public Collection<ConsumerPartitionVO> getPartitions()
   {
      return offsets.values();
   }

   public ConsumerPartitionVO getPartition(int partitionId)
   {
      return offsets.get(partitionId);
   }

   public ConsumerPartitionVO getOrCreatePartition(int partitionId)
   {
      return offsets.computeIfAbsent(partitionId, id -> new ConsumerPartitionVO(groupId, topic, id));
   }

   public double getCoveragePercent()
   {
      return (offsets.size() > 0) ? ((double)getAssignedPartitionCount()) / offsets.size() : 0.0;
   }

   public int getAssignedPartitionCount()
   {
      return (int) offsets.values().stream()
         .filter(p -> p.getOwner() != null)
         .count();
   }

   public int getOwnerCount()
   {
      return (int)offsets.values().stream()
         .map(ConsumerPartitionVO::getOwner)
         .filter(Objects::nonNull)
         .distinct()
         .count();
   }
}
